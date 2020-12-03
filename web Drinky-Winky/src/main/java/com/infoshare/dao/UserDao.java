package com.infoshare.dao;

import com.infoshare.model.Drink;
import com.infoshare.model.User;
import com.infoshare.service.MessageService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserDao {

    @PersistenceContext
    EntityManager entityManager;
    @Inject
    MessageService messageService;

    public void addFav(Long drinkId, Long userId) {
        User userById = getUserById(userId);

        List<Drink> favouriteDrinkList = userById.getFavouriteDrinkList();
        Drink drink = entityManager.find(Drink.class, drinkId);

        if (favouriteDrinkList.stream().anyMatch(e -> drinkId.equals(e.getId()))) {
            favouriteDrinkList.remove(drink);
            messageService.leaveMessage(1L, "Drink was removed from favourite");
        } else {
            favouriteDrinkList.add(drink);
            messageService.leaveMessage(1L, "Drink was added to favourite");
        }
        userById.setFavouriteDrinkList(favouriteDrinkList);
    }

    public User saveUser(User user) {
        entityManager.persist(user);
        return user;
    }


    public void updateUser(User user, Long id) {
        User userToUpdate = entityManager.find(User.class, id);
        if (userToUpdate != null) {
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userToUpdate.setUserType(user.getUserType());
            userToUpdate.setLogin(user.getLogin());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setFavouriteDrinkList(user.getFavouriteDrinkList());
            entityManager.merge(userToUpdate);
        }
    }

    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public void deleteUserById(Long id) {
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public List<User> getUserList() {
        return entityManager.createNamedQuery(User.FIND_USER_LIST, User.class).getResultList();
    }

    public List<Drink> getFavouriteDrinkList(Long id) {
        return getUserById(id).getFavouriteDrinkList();
    }

    public Optional<Drink> isFavourite(String drinkName, Long id) {
        return getFavouriteDrinkList(id).stream().filter(drink -> drink.getName().equalsIgnoreCase(drinkName)).findFirst();
    }

    public User findUserByName(String name) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_BY_NAME, User.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public Optional<User> findUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_BY_LOGIN, User.class);
        query.setParameter("login", login);
        if (query.getResultList().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(query.getSingleResult());
    }

    public User getLogin(String login) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.GET_USER_BY_LOGIN, User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    public User getPassword(String password) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.GET_USER_BY_PASSWORD, User.class);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

    public User getUserByLoginAndPass(String login, String password) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.GET_LOGIN_AND_PASSWORD, User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

}
