<!DOCTYPE html>
<html lang="en">
    <body>

        <p>
            MvcContext injected = [${mvcContextInjected}]
        </p>
        <p>
            CSRF accessible = [${csrfAccessible}]
        </p>
        <p>
            Path accessible = [${pathAccessible}]
        </p>
        <p>
            Config accessible = [${configAccessible}]
        </p>
        <p>
            Encoders accessible = [${encodersAccessible}]
        </p>
        <p>
            MvcContext scope = [${mvcContextScope}]
        </p>
        <p>
            MvcContext via EL = [${mvc != null && mvc.csrf != null}]
        </p>

    </body>
</html>