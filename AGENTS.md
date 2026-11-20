# Instrucciones del proyecto Mi Agenda

Primero confirma que recibiste este encargo y audita el proyecto. No empieces cambios, despliegues ni servicios en la nube o de pago antes de presentar hallazgos y recibir autorización expresa.

Actúa como ingeniero sénior de Android, Java, WebView, HTML/CSS/JavaScript, seguridad, almacenamiento local y pruebas. Trabaja sobre una aplicación existente llamada Mi Agenda, no la rehagas desde cero. Documenta y responde en español colombiano, claro y directo. No inventes resultados ni afirmes que algo está instalado, conectado o probado en un teléfono si no lo verificaste.

Antes de editar, inspecciona todo el proyecto fuente, los recursos, el manifest, APK que te entreguen, package ID, firmas, permisos, notificaciones y persistencia. Archivos clave actuales:
- android/assets/index.html
- android/src/co/agenda/alex/MainActivity.java
- android/src/co/agenda/alex/ReminderManager.java
- android/AndroidManifest.xml

Es una app híbrida: interfaz web en WebView y funciones nativas Android en Java. Sus claves locales actuales incluyen `agenda-alexander-prototipo-v1` (actividades), `agenda-alex-finanzas-v1` (finanzas), `agenda-category-rules-v1` (reglas de categorías), `agenda-profile-v1` (perfil) y `agenda-restore-journal-v1` (recuperación de restauración). Verifica en el código el uso real antes de hacer cambios.

Entrega primero una auditoría concisa: funciones verificadas, fallos reproducibles y pasos para reproducirlos, aspectos no probados en dispositivo, riesgos de datos/firmas y plan priorizado. No cambies claves ni elimines datos sin migración reversible y pruebas. Separa claramente hechos del código, simulaciones, pruebas de emulador y pruebas en teléfono físico.

Históricamente se han generado APK de prueba con package IDs o firmas distintos. Compara package ID y certificados de firma antes de llamarla actualización. Si no son compatibles, advierte que se instala aparte, que no migra datos automáticamente y que no se debe desinstalar ni borrar la versión anterior.

Contexto: usuario en Colombia, idioma español colombiano, zona `America/Bogota`, moneda COP; usa Xiaomi con Android 15. Interfaz preferida: oscura, personalizada y clara, con controles propios, no diálogos Android grises.

Funciones de Agenda que debes preservar y verificar:
- Al no haber filtro, mostrar actividades de hoy. Permitir buscar, crear, editar, borrar y marcar hechas.
- Una actividad guarda título, fecha, hora inicial y final opcionales, notas y estado de hecha.
- Permitir varias fechas: crear un registro independiente por cada fecha, mismos datos/horarios; al editar, editar solo el registro correspondiente. Confirmar antes de guardar grupos de fechas. Rutinas no deben generar duplicados.
- Filtros por fechas concretas, días de semana, mes completo o año; el cambio de mes no debe perder selecciones. Controles de mes y calendario propios.
- Interpretar texto en español para sugerir nombre, fecha y hora. Ejemplos: hoy, mañana, pasado mañana, día de semana, «20 de diciembre», horas a. m./p. m., «3 y media». Mostrar datos interpretados para revisión y permitir corrección; fechas imposibles o ambiguas no se deben guardar a ciegas.
- Rutina sugerida inicial debe identificarse como ejemplo, permitir editar/borrar y no borrar otras actividades al quitarla.
- Usar formato horario colombiano de 12 horas en pantalla (a. m./p. m.); conservar formato interno consistente. Resolver fechas con `America/Bogota`, incluida medianoche y año nuevo.

Dictado: el micrófono usa el servicio de voz disponible en Android. El texto se puede editar; nada se guarda solo por dictar. Clasificar Agenda/Finanzas con prudencia y permitir elección manual si hay ambigüedad. No prometer dictado offline, exactitud total, susurros ni privacidad del audio: el proveedor del reconocimiento podría procesarlo externamente. Pedir permiso solo al usarlo y manejar denegación, cancelación, error y reintento.

El perfil de nombre/correo es local, no es inicio de sesión, no protege datos y no sincroniza. No conviertas sugerencias en datos personales reales ni prellenes rutinas/pagos personales sin autorización. No agregues Google Calendar, Universal, publicidad ni conexión a Nequi/Bancolombia.

Finanzas existentes que no debes romper:
- Registrar ingresos y gastos en COP, fecha, detalle y categoría opcional. Los movimientos son locales.
- Estados pendiente/pagado aplican a gastos. Pendientes no entran en totales ni gráficas hasta marcarlos pagados. Si existen ingresos pendientes heredados, no los conviertas sin querer en recibidos; el formulario de ingreso no debe mostrar estado de gasto.
- Varias fechas crean registros independientes y repiten el monto completo en cada fecha, no lo dividen. Mostrar confirmación con cantidad y total multiplicado. Edición individual por registro.
- Sin fechas seleccionadas, mostrar todos los ingresos y gastos de hoy. Con selección, mostrar movimientos de las fechas escogidas. Búsqueda por detalle/categoría/fecha debe seguir funcionando.
- Categorías del filtro reflejan solo categorías que tienen movimientos registrados en el mes o meses seleccionados; si no se seleccionan fechas, usar el mes elegido. Incluir movimientos pendientes en la lista de categorías, aunque se excluyan de gráficas/totales. No mostrar categorías solo porque existan como sugerencia/regla si no tienen registros en el periodo.
- Borrar una categoría no borra movimientos: los deja sin categoría; confirmar y explicar eliminación de reglas personales vinculadas. Reglas editables/locales. Las sugerencias generales pueden volver a sugerir categorías.
- Transferencias entre cuentas propias no son automáticamente gasto o ingreso real. Interpretar ambigüedades como ambiguas, no adivinar dirección.
- Datos de ejemplo deben estar claramente identificados como ficticios, eliminarse sin borrar registros personales y no presentarse como datos reales. Totales solo describen lo que se registró en la app; nunca equivalen al saldo bancario.

Gráficas: elegir mes y categoría; incluir solo movimientos realizados; comparar ingresos/gastos, evolución diaria y totales por categoría. Escalas separadas y cifras legibles cuando corresponda. Advertir que ejemplos ficticios afectan totales. Las listas de categorías del mes pueden incluir pendientes, aunque las sumas no.

No subas finanzas, importes, categorías, extractos, reglas ni documentos a ningún servidor. No pidas claves ni códigos bancarios. Importación futura de extracto Nequi solo sería propuesta separada: revisión previa, sin credenciales bancarias, con deduplicación y autorización explícita.

Copias de seguridad y Android:
- Existe exportación/restauración JSON local. Actualmente no está cifrada y no se envía automáticamente a servidor; debe advertirse que puede contener datos sensibles y guardarse en lugar seguro.
- Restaurar reemplaza datos solo después de validar formato y pedir confirmación. Preservar copia actual, límite de tamaño, validación y journal de recuperación de restauración interrumpida. Manejar cancelar, archivo corrupto, versión desconocida, datos inesperados, falta de espacio y errores sin perder datos.
- Probar exportar/restaurar perfil, Agenda, finanzas y reglas compatibles; una prueba debe verificar que no se dañan datos anteriores al fallar.
- Migraciones de esquema reversibles, compatibles y verificadas con copias de prueba; no tocar los datos del usuario para experimentar.

Android: la app usa WebView con almacenamiento DOM/JavaScript y puente Java limitado para voz, permisos, recordatorios y selector de archivos. Mantén permisos mínimos, valida todos los datos del puente y no expongas métodos Java innecesarios. No habilites tráfico HTTP inseguro ni acceso universal del WebView a archivos locales.

Recordatorios son locales: dependen de permisos, alarmas, ahorro de batería y fabricante; pueden retrasarse. La fuente intenta reprogramarlos al reiniciar. No funcionan si el usuario fuerza cierre. Verifica versiones soportadas, Android 15 y el comportamiento real. No digas que dictado, permisos, notificaciones o selector funcionan en el Xiaomi sin probarlo allí.

Hubo regresiones conocidas que requieren pruebas:
1. Onboarding/capa oscura podía quedar encima de la app tras activar notificaciones y tocar Empezar. Verificar completar cada paso, reiniciar y que la app siga usable; probar cancelación/permiso denegado.
2. Finanzas llegó a ocultar el ingreso Salario al filtrar. Verificar que por defecto aparezcan ingresos y gastos de hoy y que filtros/categorías/meses no escondan registros incorrectamente.
3. APK con distinta firma/package no es actualización compatible. No sugerir desinstalar versión antigua sin comparar firma/package y tener respaldo verificado.

Nueva necesidad principal: el usuario quiere escribir en el chat del asistente, por ejemplo «Agenda cita médica el 20 de diciembre a las 3 p. m.», «Pon ejercicio lunes, miércoles y viernes a las 5 durante cuatro semanas», «Muéstrame mis actividades de la próxima semana», «cambia la cita» o «cancela la clase», y que la actividad aparezca en Mi Agenda.

Esta conexión NO existe todavía. No finjas que el chat ya puede hablar con la APK. Investiga qué mecanismos oficiales y reales admite el asistente/plataforma para herramientas o conectores; no inventes endpoint, URL, API de Zapia ni soporte MCP. Si no se puede conectar desde el chat, explícalo claramente y enumera alternativas reales.

Antes de usar cualquier infraestructura, explica arquitectura, proveedor, autenticación, costos y límites del plan gratuito, pausas por inactividad, mantenimiento, privacidad, dónde quedan los datos y pasos que requieren cuenta/permiso del usuario. No crees cuentas, despliegues, bases de datos de pago ni subas datos sin autorización expresa. Si la plataforma no permite implementar la conexión, entrega diseño y prototipo separado, no una afirmación de integración lista.

Regla estricta de privacidad: sincronizar exclusivamente actividades de Agenda. Nunca enviar finanzas, categorías, importes, reglas, extractos bancarios, perfil innecesario u otros documentos. No agregar Calendar. La conexión debe estar separada a nivel de API/esquema/credenciales para que no exista ruta hacia la tabla o claves financieras.

Las operaciones remotas esperadas podrían ser: `list_activities`, `create_activity`, `update_activity`, `mark_activity_done`, `delete_activity`. Define esquemas mínimos tipados, autenticación limitada por usuario, control de acceso por registro y errores explícitos. No exponer SQL genérico, administración de la nube ni herramientas financieras al asistente de uso diario.

Requisitos de seguridad/sincronización para una futura conexión de Agenda:
- HTTPS, autenticación real, autorización explícita, revocación y aislamiento estricto entre usuarios. Sin tokens de larga duración ni secretos del backend dentro del APK, JavaScript, HTML, repositorio o logs. Nunca usar clave de administrador dentro de la app móvil.
- El usuario puede activar, pausar, desconectar y borrar sus datos sincronizados; informar con claridad qué se almacena y dónde. Sin tabla pública ni acceso anónimo.
- Funcionar offline en local. Diseñar cola de cambios, reintentos, IDs estables, control de duplicados, marcas de tiempo, conflictos de edición y borrados/tombstones. Repetir el mismo pedido no debe duplicar eventos. Documentar qué versión prevalece ante conflicto; no descartar silenciosamente datos.
- Cada creación desde chat: interpretar hora/fecha en `America/Bogota`, mostrar vista previa de título, fecha, hora y repetición; pedir aclaración si falta dato esencial o existe ambigüedad. No inventar fechas, duración, recurrencia o participantes. Solo escribir tras confirmación/flujo que exija el producto y luego verificar respuesta real. Informar éxito solo si API confirma persistencia.
- Rutinas: definir días, intervalo, comienzo, final o número de ocurrencias, excepciones y zona horaria. Mostrar conteo antes de crear. Evitar recurrencias infinitas sin petición. Si la app inicial solo admite fechas independientes, decirlo y solicitar confirmación antes de crear varias.
- Consultar, editar, marcar hecha o borrar solo Agenda y solo registros propios. Borrado con confirmación adecuada y posibilidad de revisar el elemento exacto.
- Probar credencial ausente/expirada/revocada, usuario equivocado, payload inválido, red caída, operación repetida, conflicto, borrado y sincronización; una consulta de finanzas al conector debe ser imposible por diseño.
- Nunca guardar datos privados en logs, analytics o informes de error. Valida entradas y limita tamaño/frecuencia de solicitudes.

No supongas que agregar un endpoint a Mi Agenda basta para conectarme: se requiere canal oficial del asistente, autorización y despliegue compatible. Distingue “propuesto”, “implementado en código”, “desplegado”, “autorizado por el usuario” y “probado”.

Evalúa como propuestas, no como funciones existentes ni tareas que debas activar automáticamente:
- Recurrencias editables por días, intervalo y fecha final.
- Importar PDF/CSV local de extracto Nequi con previsualización y control de duplicados; nunca pedir credenciales bancarias ni asumir conexión automática.
- Exportación financiera local CSV además de JSON.
- Cifrado/protección de copias con experiencia sencilla y recuperación clara.
- Detección de duplicados al importar o sincronizar.
- Resúmenes mensuales de agenda, filtros guardados, accesibilidad (contraste, tamaño de texto, TalkBack, navegación teclado, áreas táctiles) y rendimiento con muchos registros.
- Migración segura de datos entre APK/firmas cuando sea técnicamente posible.

Para cada mejora indica beneficio, esfuerzo/costo de mantenimiento, riesgo, impacto en privacidad, límites y cómo desactivarla. No implementes futuras funciones financieras remotas ni subas datos financieros.

Pruebas obligatorias de lógica/datos:
- Crear/editar/hecha/borrar una actividad sin alterar otras; texto largo, espacios, datos vacíos.
- Varias fechas crean registros independientes con mismas notas/horas; repetición no duplica; edición individual correcta.
- Parser de hoy/mañana/pasado mañana/días/fechas con y sin año/cambio de año/horas AM-PM/“3 y media”/ambigüedad. Rechaza fechas imposibles e intervalos inválidos.
- Probar Finanzas: ingresos y gastos del día, búsqueda, categorías por mes y meses múltiples, pendientes fuera de totales, monto completo por fecha, categorías eliminadas sin borrar movimientos, reglas, datos ficticios separables.
- Verificar explícitamente que borrar, respaldar y sincronizar Agenda nunca modifica la clave de Finanzas.

Pruebas finales:
- Copias válidas, corruptas, antiguas, grandes y restauración cancelada/fallida; datos anteriores deben conservarse al fallar.
- Android/emulador: Android 15 y tamaños móviles, onboarding/reapertura, menús, formularios, calendario múltiple, selector de hora, lector de pantalla/contraste/texto grande, dictado, permiso de micrófono/notificaciones concedido/negado/revocado, selector de archivos, reinicio, offline/online y ahorro de batería. Documenta límites del cierre forzado.
- Integración: sin token, token inválido/revocado, otro usuario, operación repetida, offline, conflicto, borrado; finanzas inaccesibles.
- No llames “probado en el Xiaomi” a un test de código, DOM, navegador o emulador. Reporta categorías separadas: pruebas automáticas, emulador, dispositivo físico y no probado.

Flujo de trabajo:
1. Audita y reporta hallazgos y riesgos.
2. Propón arquitectura, pasos y costos; espera autorización antes de crear servicios, cuentas, desplegar o incurrir costos/subir datos.
3. Implementa cambios pequeños sobre la fuente, preserva comportamiento y claves.
4. Ejecuta pruebas, corrige regresiones y explica resultados reales.
5. Entrega archivos modificados, funciones, pruebas, limitaciones y pasos de instalación.
6. Si creas APK, informa package ID, versionCode/versionName, certificado/firma, compatibilidad con APK anterior, SHA-256 y respaldo requerido. Nunca aconsejes desinstalar/limpiar versión anterior sin compatibilidad demostrada y copia verificada.
7. No publiques el proyecto ni datos en repositorios/servicios sin permiso expreso.


