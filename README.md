# Inventory

Pet-проект для организации учета компьютерной техники

### Идея проекта

Данный сервис позволяет вести учет оборудования и оргтехники на предприятии. В некоторых организациях приходится довольно часто "перетаскивать" оборудование между отделами и людьми. Бухгалтерия не может оперативно фиксировать изменения. Этот сервис позволяет фиксировать все движения и периодически выдавать данные для сверки. Системный администратор при переносе или установке нового оборудования может быстро в смартфоне сделать пометки.

Жизненный цикл работы с сервисом представляется следующим. Пользователь проходит регистрацию в сервисе, то есть создает себе аккаунт, представляющий сочетание логина(адреса электронной почты) и пароля. На почту приходит ссылка с подтверждением регистрации. После этого он становится администратором организации. В своем личном кабинете он может редактировать данные организации (название), создавать список отделов (отдел/филиал/участок/кабинет), создавать новых пользователей. Роли пользователей создаются при создании (администратор/клиент). Позже их можно изменять. У отделов меняется только название. Также создается список категорий оборудования (например мониторы, МФУ, телефоны). Оборудование у каждого пользователя будет разбито по этим категориям. Без категорий оборудования быть не должно. Дальше создается список оборудования (инв. номер, название, категория, дата принятия, подотчетное лицо и фактический пользователь). Основная особенность состоит в том, что оборудование может числиться за одним сотрудником, а фактически им пользуется совсем другой человек. Должен быть как минимум один пользователь (он же администратор) ресурса для каждой организации.

### Требования
* Приложение представляет собой api-сервис
* Авторизация пользователей
* Поддержка двух ролей пользователей: администратор и клиент
* Спецификация предоставляется в редакторе Swagger 
* Хранение истории перемещений

### Функции администратора
* Создание, редактирование пользователей
* Редактирование данных организации
* Создание, редактирование категорий оборудования
* Создание, редактирование подразделений
* Создание, редактирование списка оборудования
* Перемещение оборудования между подотчетными лицами

### Функции пользователя
* Просмотр всего списка оборудования 
* Поиск оборудования по инвентарному номеру
* Фильтрация оборудования по нескольким параметрам (категория, подотчетное лицо, фактический пользователь, отдел)

### Задумки для развитие
В дальнейшем, желательно сделать front-end часть приложения с простым и понятным интерфейсом. Никаких форм строгой отчетности выводить не планируется.  

### Используемый стек
Java, Spring boot, Hibernate, PostgreSql, Docker, Swagger, Dbdiagram.io 

### Дневник изменений
Изменения можно посмотреть по коммитам. Здесь же будет отражен ход мыслей, что хотелось сделать и что пришлось 
переделать. 

Составлена er-диаграмма фиксированного состояния системы. Здесь может быть несколько организаций. У каждой и них может быть несколько категорий, пользователей и отделов. Само оборудование привязано к конкретной категории. По этой схеме можно сделать выборки оборудования по разным параметрам: категория, подотчетному лицу, фактическому пользователю, отделу, названию.
<img width="100%" src="er_diagram/inventory_01.png" alt="Диаграмма фиксированного состояния">

Добавлена таблица для фиксации перемещений. Здесь можно делать, что и ранее, но и история будет записана. Не совсем хорошо, что для переноса оборудования от одного подотчетного лица к другому либо при перемещении между отделами придется сделать два запроса (запись в таблицу items и movements)
<img width="100%" src="er_diagram/inventory_02.png" alt="Диаграмма фиксированного состояния c историей">

Изменена схема БД. Теперь привязка оборудования к пользователям и отделам идет только в таблице moving. Плохо только, что оборудование может быть некоторое время без связи к лицу и отделу. При создании оборудования нужно сделать два запроса (запись в таблицу items и movements), но при перемещениях, а это большая часть работы сервиса будет необходим только один запрос.
<img width="100%" src="er_diagram/inventory_03.png" alt="Все связи в истории">

Инвентарный номер следует сделать строкой, так и искать проще будет (по части номера) и возможны разные буквенные префиксы.

Оказывается, слово "end" нельзя использовать в качестве названия поля в PostgreSql. Переименовал пару полей(start->created, end->finished)

Администратор может создавать новое перемещение. Это хорошо, а если вдруг ошибся и не туда засандалил? Можно ли исправлять? Если да, то возможны проблемы при исправлении давнешнего перемещения, ведь все, что после этого тоже поплывет. Хотя можно заменять две записи в БД (в предыдущей испрвлять to_owner_id, в текущей to_owner_id). Тогда все будет ОК. При создании перемещения тоже будет две записи. Выгоды по сравнению со второй схемой сомнительна. Только запросы сложнее. Но пока так поковыряюсь.

Изменил схему БД. Теперь в одно перемещение можно включать целый список оборудования. Вроде как так легче изменения вносить и убираем дублирующую информацию.

<img width="100%" src="er_diagram/inventory_04.png" alt="Перемещение списком">

Для регистрации новой организации нужно поле с кодом активации и указанием, что активация успешна.
Добавил нужные поля в схему БД.

<img width="100%" src="er_diagram/inventory_05.png" alt="Перемещение списком">

Добавил поля from_user_id и to_user_id в перемещения, ведь ключевая информация - это то, где оборудование фактически находится.

<img width="100%" src="er_diagram/inventory_06.png" alt="Перемещение списком">

Добавил поля user_id, owner_id, depapartment_id в items, поиск будет гораздо легче (меньше запросов к бд). Хотел в таблицу items добавить и поле organization_id, но че-то слишком на дублирование похоже. Это поле можно взять из категорий.

<img width="100%" src="er_diagram/inventory_07.png" alt="Перемещение списком">

Теперь можно взяться за рефакторинг. В каждом сервисе есть несколько подключений к репозиториям. Получаются одинаковые методы в разных сервисах. Но просто так сервисы нельзя инжектить, наблюдается цикличное внедрение. Потому выбран путь создания базовых сервисов, где идет подключение только своего репозитория и этот сервис будет реализовывать только основные функции по поиску и проверке существования, также методы по сохранению - это конечно простое делегирование.

Наконец-то переименовал поля user_id в таблицах items и movements. Теперь немного меньше путаницы. 
<img width="100%" src="er_diagram/inventory_08.png" alt="Перемещение списком">

Обработку ошибок добавил, скопировав все из учебного проекта. 

Сделал сервис для логирования сообщений. Пока не использую.

Нужно тестировать.