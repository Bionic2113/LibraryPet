delete from bookspeople;
delete from  books;
insert into books (id,title,author) values (1,'Мцыри', 'М.Ю.Лермонтов'),
                                           (2,'Ася', 'И.С.Тургенев'),
                                           (3,'Собачье Сердце', 'М.А.Булгаков');
insert into books (id,title,author) values (177,'Мастер и Маргарита', 'М.А.Булгаков');
insert into books(title,author,active) values ('Kick','Me',false);