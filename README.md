Custom UsersRolesLoginModule for EAP 7
========================================

Setup
-------
- Clone repository, run `mvn package` on repo, and copy target/jboss-custom-login.war to your ${JBOSS_HOME}/standalone/deployments/ directory
- Make sure you have a users.properties file and roles.properties file in your ${JBOSS_HOME}/standalone/configuration directory. The default role to login is "user_role," which can be changed in src/main/webapp/web.xml
- Update ${JBOSS_HOME}/standalone/configuration/standalone.xml to define the security domain, either by replacing the file with etc/standalone.xml, or manually including the following:
```
<security-domain name="form-auth" cache-type="default">
    <authentication>
        <login-module code="custom.MySimpleUsersRolesLoginModule" flag="required">
            <module-option name="usersProperties" value="${JBOSS_HOME}/standalone/configuration/users.properties"/>
            <module-option name="rolesProperties" value="${JBOSS_HOME}/standalone/configuration/roles.properties"/>
        </login-module>
    </authentication>
</security-domain>
```
- Restart EAP and the login module should appear at [http://localhost:8080/jboss-custom-login](http://localhost:8080/jboss-custom-login).
