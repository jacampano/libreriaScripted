## Uso 

Una vez se ha realizado el registro en la configuración de Jenkins, para hacer uso de la librería solo será necesario referenciarla desde el Pipeline de la siguiente forma:

```
@Library('Library Name')
```

Por ejemplo, si a nuestra libreria la hemos llamado libreriaScripted, en nuestro pipeline deberemos referenciarla:

```
@Library('libreriaScripted')
```



