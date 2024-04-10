-- :name update-last-login! :! :n
UPDATE account
SET last_login = :last_login
WHERE id = :id;

-- :name get-account :? :1
-- :doc retrieves account info based on email and password
SELECT a.id, a.email, a.password, a.full_name, a.active, a.last_login,
        at.name as type, ast.name as state
FROM account a inner join account_type at on a.account_type_id = at.id
    inner join account_state ast on a.account_state_id = ast.id
WHERE a.email = :email;

-- :name get-account-by-id :? :1
SELECT a.id, a.email, a.password, a.full_name, a.active, a.last_login,
       at.name as type, ast.name as state
FROM account a inner join account_type at on a.account_type_id = at.id
               inner join account_state ast on a.account_state_id = ast.id
WHERE a.id = :id;

-- :name create-session :! :n
INSERT INTO session
(user_id)
VALUES (:identity);

-- :name get-session :? :1
SELECT a.id, a.email, a.full_name, a.active, a.last_login,
        at.name as type
FROM account a
    inner join account_type at on a.account_type_id= at.id
    inner join session s on s.user_id = a.id
WHERE a.id = :identity
LIMIT 1;

-- :name delete-session! :! :n
DELETE FROM session
WHERE user_id = :identity;

-- :name get-roles :? :*
SELECT at.name as role
FROM account_roles ar
    inner join account_type at on ar.account_type_id = at.id
WHERE ar.account_id = :identity;

-- :name get-account-by-token :? :1
-- :doc retrieves account info based on unique user generated tokens
SELECT a.id, a.email, a.password, a.full_name, a.mobile, a.active, a.last_login,
        ast.name as state, at.name as type
FROM account a inner join account_type at on a.account_type_id = at.id
             inner join account_state ast on a.account_state_id = ast.id
WHERE a.token = :token;

-- :name update-account! :! :n
UPDATE account
SET email = :email,
    account_state_id = :state,
    token = :token
WHERE id = :id;

-- :name update-account-password! :! :n
UPDATE account
SET password = :pwd
WHERE id = :id;

-- :name update-account-state! :! :n
UPDATE account
SET account_state_id = :state_id
WHERE id = :id;

-- :name get-all-user-groups :? :*
SELECT * FROM account_type;

-- :name create-user! :! :n
INSERT INTO account (email, password, full_name, mobile, token, last_login, active, account_type_id, account_state_id)
VALUES (:email, :password, :full_name, :mobile, :token, :last_login, :active, :account_type_id, :account_state_id);

-- :name update-user! :! :n
UPDATE account
SET password = :password,
    full_name = :full_name,
    mobile = :mobile,
    active = true,
    token = null,
    account_state_id = :state_id
WHERE
    id = :id;

-- :name update-account-roles! :! :n
INSERT INTO account_roles (account_id, account_type_id)
VALUES (:id, :group_id);

-- :name get-properties :? :*
SELECT * FROM property;

