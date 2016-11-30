Custom UsersRolesLoginModule for EAP 7
========================================

Setup
-------
- Clone repository, run `mvn package` on repo, and copy target/jboss-custom-login.war to your ${JBOSS_HOME}/standalone/deployments/ directory
- Update ${JBOSS_HOME}/standalone/configuration/standalone.xml to define the security domain by including the following in `<security-domains>`:
```
<security-domain name="form-auth" cache-type="default">
    <authentication>
        <login-module code="custom.CustomLoginModule" flag="required"/>
    </authentication>
</security-domain>
```
- Restart EAP and the login module should appear at [http://localhost:8080/jboss-custom-login](http://localhost:8080/jboss-custom-login).
