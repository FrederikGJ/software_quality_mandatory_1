# Black Box Test 

Her vil jeg have spørgsmål der hjælper til at finde 2 ud af 4 almindelige black box test teknikker.

Det er equvivalence partitioning og boundary value analysis.

## Equvivalence Partitioning

### Hvilke inputs er der?

### Hvordan skal de grupperes i partitioner?

#### Hvilke valide partitions er der? 

#### Hvilke invalide partitions er der?

#### Edge cases - hvilke edge cases kan der være i vher partition (fx send en Sting in hvor der skal være float som input)?

#### Er der output partitioner fx fra if-else som hvor vi skal teste begge udfald?

### Tallet 0 
Hvis der er tal er 0 et sprcielt tal - den får sin egen partition da den opfører sig anderledes end både postitive og negative tal. 

## Boundary Value Anlalysis

- særlig obs punkt er at <= og < er forskellige. Og vi skal teste derefter (eller kidlekoden kan være forkert).

### Hvilke boundaries er der for partitionerne? (der skal testes på hver side af dem samt lige på grænsen dvs. 3 test)

- Husk at der skal være flest negative test - det er nemt at teste "happy path", det er svært at sørge for at systemet fejler korrekt. 
- Derfor laver vi altid en faktor 2-3 ratio af negative test til positive.
