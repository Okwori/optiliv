-- :name update-last-login! :! :n
UPDATE account
SET last_login = :last_login
WHERE id = :id;

-- :name get-account :? :1
-- :doc retrieves account info based on email and password
SELECT a.email, a.password, a.full_name as "full-name", a.active, a.last_login as "last-login",
        at.name as "type", at.id
FROM account a
    inner join account_type at on at.id = a.account_type_id
WHERE email = :email;

-- :name create-session :! :n
INSERT INTO session
(user_id)
VALUES (:identity);

-- :name get-session :? :1
SELECT a.email, a.full_name as "full-name", a.active, a.last_login as "last-login",
        at.name as "type", at.id
FROM account a
    inner join account_type at on at.id = a.account_type_id
    inner join session s on s.user_id = a.id
WHERE s.user_id = :identity
LIMIT 1;

-- :name delete-session! :! :n
DELETE FROM session
WHERE user_id = :identity;

-- :name get-roles :? :*
SELECT at.name as role
FROM account_roles ar
    inner join account_type at on ar.account_type_id = at.id
WHERE ar.account_id = :identity;

-- :name get-roles :? :*
SELECT at.name as role
FROM account_roles ar
    inner join account_type at on ar.account_type_id = at.id
WHERE ar.account_id = :identity;

