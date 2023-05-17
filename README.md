# Foodies
  Тестовое задание
  
  Для навигации используется библиотека decompose, для многопоточности - корутиры, для интерфейса - конечно же compose.
  Ну и по сути это все интересные здесь фреймворки 

  Главная сущность в программе - это products (StateFlow<List<Product'>>)
  
  При запуске программы MainComponent получает products из репозитория, и передает его в дочерние компоненты, которые подписываются на изменения сущности.
  
  Изеняет (изменияет поле quantity, символизируя добавление в корзину) products только AddInCartComponent.
  
  Схему программы можно представить так:
  
![image](https://github.com/PetrushkaCat/Foodies/assets/107431204/526943e7-7231-457e-a9ab-bd4b19a7a9fa)

  У каждого компонента есть свой экран (или часть). Друг с другом они взаимодействуют при помощи паттерна mvvm + mvi :)
  
  Их еще попробуй разлечи, хпх
  
  *Не совсем пока понимаю как пофиксить обрывистую анимацию смены категории и сделать прокрутку при нажатии на категорию более плавной :(*
  
![image](https://github.com/PetrushkaCat/Foodies/assets/107431204/4cd33de5-cf37-4075-8352-6d1110b8be54)
![image](https://github.com/PetrushkaCat/Foodies/assets/107431204/6bb6a01f-0677-4d8f-a7a0-285a53a20491)
![image](https://github.com/PetrushkaCat/Foodies/assets/107431204/fc71cc00-7cd1-4eb0-bbab-6f6d4827eab4)
![image](https://github.com/PetrushkaCat/Foodies/assets/107431204/f1b4f8b3-c7c0-4531-9da1-38c6be5a6680)
![image](https://github.com/PetrushkaCat/Foodies/assets/107431204/0cc7d89d-05fe-4f54-943f-c527ac1c171b)
![image](https://github.com/PetrushkaCat/Foodies/assets/107431204/029cb996-5f9b-4d14-acd6-27b54443455a)
