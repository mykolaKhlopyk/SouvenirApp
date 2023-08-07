# SouvenirApp
GeeksForLess module

## ProducersService methods 
- getById(int id)
- save(Producer producer)
- update(Producer producer)
- getAll()
- getSouvenirsOfProducer(Producer producer)
- getAllWithPricesOfSouvenirsLessThan(double maxPrice)
- getProducersWithTheirSouvenirs()
- deleteProducerAndHisSouvenirs(Producer producer)

## SouvenirsService methods
- getById(int id)
- save(Souvenir souvenir)
- update(Souvenir updatedSouvenir)
- getAll()
- getByCountry(Country country)
- getProducersOfSouvenirNameCreatedAt(String souvenirName, int year)
- getMapYearSouvenirs()

## Technologies
- java17
- maven
- junit5
- lombok

Implemented Unit tests ProducerServiceImplTest, SouvenirsServiceImplTest (run separately). <br/>
Used 3 files for separately saving producers, souvenirs and names of souvenirs

