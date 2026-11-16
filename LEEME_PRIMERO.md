# Calculadora de pagos — obtener una APK SIN Android Studio

Este ZIP contiene el proyecto COMPLETO y un archivo de automatización de GitHub Actions. No necesitas crear proyectos, instalar Gradle, abrir Android Studio, ni copiar archivos de una plantilla a otra.

## PASO 1 — Crear un repositorio gratuito en GitHub

En tu computadora abre https://github.com/new, inicia sesión o crea una cuenta, escribe un nombre (por ejemplo `MiCalculadoraPagos`), selecciona **Private** y crea el repositorio (Create repository).

## PASO 2 — Subir el proyecto en una sola operación

1. Extrae este ZIP en tu computadora.
2. En el repositorio nuevo, abre **uploading an existing file** o **Add file → Upload files**.
3. Abre la carpeta extraída y **arrastra todos los archivos y carpetas que están dentro** a la página de subida. Incluye la carpeta **`.github`**. IMPORTANTE: no subas el ZIP cerrado, ni arrastres la carpeta exterior como subcarpeta: los archivos `settings.gradle`, `build.gradle` y `app/` deben quedar en la raíz del repositorio. `.github/workflows/crear-apk.yml` también debe conservar esa ruta.
4. Pulsa **Commit changes** (guardar cambios).

## PASO 3 — Descargar la APK

1. En la página de tu repositorio entra en **Actions**.
2. Abre **Crear APK de la calculadora**, y elige la ejecución del commit que subiste (puede aparecer automáticamente unos segundos después; si no, pulsa **Run workflow** y selecciona `main`).
3. Cuando el resultado sea ✅, abre esa ejecución, baja a **Artifacts** y descarga **Calculadora-de-Pagos-APK**.
4. El archivo descargado por GitHub es un ZIP: descomprímelo para obtener **app-debug.apk**.
5. Mueve **app-debug.apk** a tu Samsung (USB, transferencia de archivos, mensajería personal, etc.), tócalo para instalarlo y acepta la instalación desde esa fuente cuando Android lo solicite.

La compilación inicial descarga dependencias desde Google/Gradle; la APP INSTALADA no necesita internet ni permisos bancarios.

## Importante

- **No es una APK precompilada.** El ZIP contiene un procedimiento automatizado para que GitHub compile una APK auténtica.
- **APK de desarrollo** (`app-debug.apk`), válida para uso personal y pruebas, con firma de desarrollo. GitHub puede usar una firma diferente en una compilación posterior. Si quieres instalar una versión nueva encima de esta, habrá que configurar una firma privada persistente, o desinstalar primero la anterior (esta calculadora no guarda operaciones).
- Si deseas distribuirla ampliamente, conviene firmar una versión release con una clave privada propia.
- La calculadora funciona sin conexión y NO es la app oficial de Ubii. Calcula estimaciones del 0,5% de comisión por recarga y del posible 9% de gastos administrativos según el modo seleccionado. No garantiza cancelar la deuda exacta.
- Si GitHub te pide habilitar Actions, actívalo para este repositorio. No compartas códigos bancarios, contraseñas ni archivos financieros en el repositorio.
- Esta solución no se ha compilado aquí: no hay Android SDK ni Gradle ni acceso a sus descargas en este entorno. El workflow está preparado según las versiones del proyecto; si alguna herramienta cambia en GitHub, se deberá ajustar.

## Estructura

- `app/src/main/assets/index.html`: calculadora offline
- `app/src/main/java/.../MainActivity.java`: app Android
- `app/src/main/AndroidManifest.xml`: declaración de la app
- `app/src/main/res/`: iconos y tema
- `.github/workflows/crear-apk.yml`: compilación automática de la APK
- `build.gradle`, `settings.gradle`, `gradle.properties`, `app/build.gradle`: configuración

## Resultado esperado

`app/build/outputs/apk/debug/app-debug.apk`
