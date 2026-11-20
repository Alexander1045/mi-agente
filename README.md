# Mi Agenda — paquete de trabajo para OpenCode

Este paquete se puede extraer en el computador y abrir como carpeta de proyecto en OpenCode.

## Contenido
- `AGENTS.md`: especificación completa y reglas que OpenCode debe seguir.
- `TAREA-INICIAL.md`: primer encargo; empezar auditando, sin modificar nada.
- `android/`: fuentes y recursos Android disponibles.
- `Mi-Agenda-APK-referencia.apk`: APK para inspección/comparación, no como instalación recomendada.
- `SHA256-APK.txt`: hash de la APK de referencia.

## Nota sobre la compilación
La fuente disponible incluye el HTML/JavaScript, el Manifest, recursos y clases Java. **No incluye un proyecto Gradle completo, Gradle Wrapper ni configuración de compilación verificable.** Pídele a OpenCode que identifique los archivos/versiones faltantes y proponga cómo reconstruir un proyecto compilable sin cambiar primero las funciones ni datos.

## Seguridad y datos
La app guarda actividades y finanzas localmente. No hay conexión entre esta app y el chat de Zapia ni Google Calendar. No subas finanzas a la nube. La APK de referencia no demuestra instalación, actualización compatible, migración ni funcionamiento en el Xiaomi. No desinstales versiones previas ni borres datos.

## Inicio sugerido
1. Extrae el ZIP en tu computador.
2. Abre la carpeta `mi-agenda-para-opencode` en OpenCode.
3. Pídele que lea `AGENTS.md` y siga `TAREA-INICIAL.md`.
4. Revisa su auditoría y autoriza cambios después de ver el plan.
