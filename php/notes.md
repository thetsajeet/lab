# PHP

- PHP (recursive acronym for PHP: Hypertext Preprocessor) is a widely-used open source general-purpose scripting language that is especially suited for web development and can be embedded into HTML.
- The PHP code is enclosed in special start and end processing instructions <?php and ?> that allow jumping in and out of PHP mode.
- What distinguishes PHP from something like client-side JavaScript is that the code is executed on the server, generating HTML which is then sent to the client. The client would receive the results of running that script, but would not know what the underlying code was. A web server can even be configured to process all HTML files with PHP, and then there's no way that users can tell that PHP is being used.
- Mostly server-side scripting language
- Server side scripting is possible because of PHP parser, Web server and Web browser
- To run .php files, first put it inside apache or any web server (under /var/www/html) and open in the browser
- php is embedded inside html: eg:

```php {init.php}
<html>
    <body>
        <?php echo "hello world from php" ?>
        <?php if (str_contains($_SERVER['HTTP_USER_AGENT'], 'brave')) {
        ?>
        <h3> you are using brave browser </h3>
        <?php
        } else {
        ?>
        <h3> you are not using brave browser </h3>
        <?php
        }
        ?>
        <?php phpinfo(); ?>
    </body>
</html>
```
