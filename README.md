# word-to-pdf

[![Testing](https://github.com/ebukreev/word-to-pdf/actions/workflows/testing.yml/badge.svg?branch=master)](https://github.com/ebukreev/word-to-pdf/actions/workflows/testing.yml)

Software Engineering 2022 labs

Simple service that takes .doc or .docx file with HTTP POST request Multipart body and return that file but converted to pdf.

To run app:

```
./gradlew buildFatJar
```

and then 

```
java -jar ./build/libs/converter.jar
```

To convert Word file you can 

```
curl -F uploads=@word/file/path.docx -X POST http://0.0.0.0:8080/api/convert --output output.pdf
```

To run with docker:

```
docker build -t converter .
```

and then

```
docker run -p 8080:8080 converter
```