1. Разработать микросервис (далее MS) bankAccount, который генерирует банковские аккаунты из .txt файлов в директории accounts
	public class BankAccount {
		private UUID uuid;
		private String firstName;
		private String lastName;
		private String patronymic;
		private long accountNumber;
	}
2. Разработать MS kafka-Producer, который получает через REST API аккаунты по @Sheduler с MS bankAccount, добавляет новый параметр AccountType, фильтрует по условию, записывает в очередь в Kafka.
	public class BankAccount {
		private UUID uuid;
		private String firstName;
		private String lastName;
		private String patronymic;
		private long accountNumber;
		private AccountType accountType;
	}
3. Разработать MS kafka-Consumer, который получает из Kafka банковские аккаунты (BankAccount) и по @Sheduler’у записывает в базу данных Cassandra.
4. Разработать MS user-cassandra-request, который при обращении Rest по uuid получает из Cassandra банковский аккаунт.
5. Разработать MS address, который получает из потока созданных аккаунтов только те фамилии, которые начинаются на заглавную ‘А’ и генерирует из них рандомный Address из стороннего ресурса https://randomapi.com/
	public class Address {
		String street;
		String city;
		String state;
	}
6. Изменить работу MS kafka-consumer: Берет из kafka KTable аккаунтов и KTable адресов, выполняет Join данных в отдельный поток, при обновлении данных автоматически записывает в базу данных Cassandra.
	public class BankAccountInfo {
		private BankAccount bankAccount;
		private Address address;
	}
7. Изменить MS user-cassandra-request таким образом, чтобы по запросу uuid возвращался json объект BankAccountInfo и входящие в него BankAccount и Address.
8. Разработать MS gRpc-service. Данный сервер gRpc ходит в Cassandra и по запросу AccountType выдает список BankAccountInfo.
9. Разработать MS gRpc-client-service. По rest запросу к данному MS выдает через соединение с gRpc-service список BankAccountInfo. Парсит модели(из генерируемых .proto в обычные модели) и выдает пользователю.
10. Разработать MS RSocket-server, который обращается к Cassandra через реактивный репозиторий (ReactiveRepository).
11. Разработать MS RSocket-client, который по rest запросу выдает список всех BankAccountInfo (request-stream), BankAccountInfo по uuid (request-response), удаление BankAccountInfo по uuid (fire-forget).
12. Добавить к MS тесты с использованием Mockito и JUnit.