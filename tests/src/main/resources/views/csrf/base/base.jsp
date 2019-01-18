<!DOCTYPE html>
<html lang="en">
    <body>

        <!-- Test if Csrf is available -->
        <div>
            <span id='csrf-injected'>${injectedCsrf != null}</span>
            <span id='csrf-el'>${mvc.csrf != null}</span>
        </div>

        <!-- Dummy form -->
        <form action="post" onsubmit="return false;">
            <input id="token" type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>
        </form>

    </body>
</html>