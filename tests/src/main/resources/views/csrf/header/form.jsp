<!DOCTYPE html>
<html lang="en">
    <body>

        <form id="form" action="./process" method="post">
            <input id="token" type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>
            <input id="input" type="text" name="name">
            <input id="submit" type="submit">
        </form>

    </body>
</html>