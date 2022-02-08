insert into wallet (player_id, balance, last_updated) values ('player1', 0.0, CURRENT_TIMESTAMP());
insert into wallet (player_id, balance, last_updated) values ('player2', 0.0, CURRENT_TIMESTAMP());

insert into transaction (type_id, amount, wallet_id, last_updated, last_updated_by) values ('DEPOSIT', 500, '1', CURRENT_TIMESTAMP(), 'admin');
insert into transaction (type_id, amount, wallet_id, last_updated, last_updated_by) values ('REFUND', 100, '1', CURRENT_TIMESTAMP(), 'admin');
insert into transaction (type_id, amount, wallet_id, last_updated, last_updated_by) values ('WITHDRAWAL', 500, '1', CURRENT_TIMESTAMP(), 'admin');

insert into transaction (type_id, amount, wallet_id, last_updated, last_updated_by) values ('DEPOSIT', 5000, '2',CURRENT_TIMESTAMP(), 'admin');
insert into transaction (type_id, amount, wallet_id, last_updated, last_updated_by) values ('WITHDRAWAL', 10, '2', CURRENT_TIMESTAMP(), 'admin');
insert into transaction (type_id, amount, wallet_id, last_updated, last_updated_by) values ('WITHDRAWAL', 10, '2', CURRENT_TIMESTAMP(), 'admin');
