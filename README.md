# Tinkoff_random_clients
Программные требования:
- На компьютере должна быть установлена jdk от версии 1.8.

Запуск:
1. В командной строке переходим в папку с приложением cd "путь до папки Tinkoff_random_clients"
2. Дале пишем gradlew clean build
3. Далее переходим в папку \build\libs
4. В cmd меняем кодировку командой "chcp 1251" для корректного отображения русских букв в консоли
5. В cmd пишем "java -jar arhitecturetask-1.0.jar"
6. Сгенерированные два файла будут лежать в той же папке (Tinkoff_random_clients\build\libs)


Информация:
1. Файлик конфига: src/main/resources/hibernate.cfg.xml
2. <property name="connection.url"> - урл покдлючения к базе
3. <property name="connection.username">root</property>
<property name="connection.password"></property> - данные для входа
