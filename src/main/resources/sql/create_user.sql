INSERT INTO users (name, username, password, email, phone)
VALUES (:#${body.name}, :#${body.username}, :#${body.password}, :#${body.email}, :#${body.phone})
