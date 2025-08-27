# KtorApiClient.kt

Este archivo define un **cliente HTTP con Ktor** para conectarse a la API pública de [`https://dummyjson.com/`](https://dummyjson.com/).

---

##  Configuración del cliente

- **Motor de red:** Usa `OkHttp`.
- **ContentNegotiation:** Configurado con `kotlinx.serialization.json` para trabajar con JSON.
    - `ignoreUnknownKeys = true` asegura que si la API devuelve campos extra, no rompa la deserialización.
- **Logging:** Muestra información básica de las peticiones y respuestas (`LogLevel.INFO`).
- **DefaultRequest:** Define la base URL (`https://dummyjson.com/`), así todas las peticiones parten de ahí.

---

## Métodos disponibles

### 1. `getUsersPage(skip, limit)`

- Hace una petición `GET /users` con parámetros `skip` y `limit`.
- Devuelve un `UsersResponse` con la lista de usuarios de esa página.

**Ejemplo de llamada real:**

```bash
GET https://dummyjson.com/users?skip=0&limit=208
```
### 2. `getAllUsers()`

Este método se encarga de **descargar la lista completa de usuarios** desde la API, pero la API solo permite pedir un número limitado de registros por petición.

- **Primer request:**  
  Se hace una llamada inicial a `getUsersPage(skip = 0, limit = 208)`.  
  Esto devuelve hasta **208 usuarios** y además incluye el campo `total` (cantidad total de usuarios en la API).

- **Paginación automática:**  
  Con el valor de `total`, el método entra en un bucle:  
  - Mientras `skip < total`, sigue haciendo más peticiones con el mismo `limit = 208`.  
  - Cada página descargada se agrega a la lista `allUsers`.

- **Acumulación de resultados:**  
  Los usuarios de cada página se añaden a una lista mutable (`mutableListOf<User>`).  
  Al final del proceso, la función devuelve la lista completa de usuarios.

**Ejemplo simplificado del flujo:**

1. Pide los primeros 208 usuarios (`skip = 0`).  
2. Si el total es mayor a 208, hace otra petición (`skip = 208`).  
3. Sigue repitiendo (`skip = 416`, `skip = 624`, …) hasta cubrir todos los registros.  
4. Une todos los usuarios en una sola lista y la devuelve.  


# MainScreen.kt

Este Composable representa la pantalla principal donde se visualiza una lista de usuarios.

- Utiliza un LazyColumn para mostrar los elementos de forma eficiente.

- Tiene una stickyHeader que muestra el número total de usuarios registrados.

- Cada usuario se renderiza con el componente UserListItem.

- Cuando se hace clic en un usuario, se ejecuta la función onUserClick, que se pasa como parámetro 
  para manejar la navegación o la acción correspondiente.

#  UserListItem.kt
Este Composable representa un elemento de la lista de usuarios dentro de la aplicación.

- Se muestra en forma de Card con un diseño limpio y elevado ligeramente.

- Contiene:

  - Foto del usuario en forma circular (cargada con Coil).

  - Nombre y apellido en estilo destacado.

  - Nombre de la empresa en un estilo secundario.

  - Un ícono (ChevronRight) que indica que el ítem es seleccionable.

- Al presionarlo, ejecuta la acción onClick, lo que permite usarlo para navegar o mostrar más detalles del usuario.



# DetailScreen.kt

Este archivo define la **pantalla de detalle de un usuario** en la aplicación.

---

# Funcionalidad

- Muestra la información completa de un usuario seleccionado.
- Contiene una **imagen grande** del usuario en la parte superior.
- Incluye datos personales como:
    - Nombre completo
    - Empresa
    - Teléfono (clickeable: abre el marcador de llamadas)
    - Email
    - Edad
    - Género
    - Altura
    - Peso
    - Universidad

---

##  Componentes principales

### `DetailScreen(user: User)`
Composable que representa la pantalla completa.
- Usa un `Surface` como contenedor principal.
- Organiza los elementos en un `Column` centrado.
- Incluye un `AsyncImage` para la foto del usuario.
- Muestra el nombre en texto destacado.
- Lista de información adicional (llamando a `Info`).

### `Info(label, value, isLink, onClick)`
Composable auxiliar que muestra una línea de información en formato `Etiqueta: Valor`.
- Si `isLink = true`, el texto se pinta con el color primario y es clickeable.
- En el caso del teléfono, abre el **dialer** del dispositivo (`Intent.ACTION_DIAL`).

---

##  Ejemplo de uso

```kotlin
DetailScreen(user = selectedUser)
```

# NavigationStack.kt

Este archivo define el **sistema de navegación de la app** usando `NavHost` de Jetpack Compose.

---

##  Estructura de navegación

Se usan **rutas** definidas en la clase `Screen`:

- **Main** → `"main"`  
  Pantalla principal que muestra la lista de usuarios.  
- **Detail** → `"detail?userId={userId}"`  
  Pantalla de detalle para un usuario específico, recibiendo su `id` como argumento.

---

##  Flujo

1. La app inicia en `Screen.Main` (lista de usuarios).  
2. Al seleccionar un usuario, se navega a la ruta:  
3. `Screen.Detail` recibe el argumento `userId`, busca el usuario correspondiente en la lista ya cargada, 
    y abre la pantalla `DetailScreen`.

---

##  Carga de datos

- Los usuarios se cargan desde la API (`KtorApiClient.getAllUsers()`) **una sola vez** al iniciar el `NavigationStack` usando `LaunchedEffect(Unit)`.
- Esta lista (`users`) se comparte entre pantallas sin necesidad de volver a pedir datos.

---

##  Componentes principales

### `sealed class Screen`
Define de forma centralizada las rutas de navegación.  
Evita errores de strings hardcodeados.

### `NavigationStack()`
Composable que:
- Inicializa el `NavController`.
- Carga los usuarios desde la API.
- Configura el `NavHost` con los destinos `Main` y `Detail`.

---

## ✅ Ejemplo de navegación

```kotlin
// Desde MainScreen:
nav.navigate("detail?userId=${user.id}")
```
// En DetailScreen:
val id = backStackEntry.arguments?.getInt("userId")
val user = users.firstOrNull { it.id == id }
