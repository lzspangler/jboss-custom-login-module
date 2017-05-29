Custom UsersRolesLoginModule for EAP 7
========================================

Setup
-------
## Clone and Install the Custom Login Module
- Clone repository and add necessary dependency for the PWSecurity classes to the pom

- Run 'mvn clean install' to compile 

- Copy the /target/jboss-custom-login.jar to ${JBOSS_HOME}/standalone/deployments/business-central.war/WEB-INF/lib

- Update ${JBOSS_HOME}/standalone/configuration/standalone.xml to define the security domain by including the following in `<security-domains>`:

            <security-domain name="other" cache-type="default">
               <authentication>
                  <login-module code="custom.CustomLoginModule" flag="required"/>
      			  <login-module code="org.jboss.security.auth.spi.RoleMappingLoginModule" flag="optional">
      			  	<module-option name="rolesProperties" value="file:<path to rolemapping file>/rolemapping-roles.properties"/>
      			  	<module-option name="replaceRole" value="true"/>
      			  </login-module> 
               </authentication>
            </security-domain>

##Note
In the above be sure to change `<path to rolemapping file>` to the actual path where the rolemapping-roles.properties will be placed. An example rolemapping-roles.properties can be found in the /properties directory of this project.

- Enter the proper PWSecurity permission code to Business Central role and group mappings in the rolemapping-roles.properties and copy it to the location corresponding to the standalone config


## Setup Login Module Properties
- Create the directory ${JBOSS_HOME}/modules/conf/main/properties

- Place the example module.xml (in /properties of this project) into ${JBOSS_HOME}/modules/conf/main

- Add the module as a global module in the standalone.xml: 

        <subsystem xmlns="urn:jboss:domain:ee:1.2">
            <global-modules>
                <module name="conf" slot="main"/>
            </global-modules>


- Place the example login-module.properties (also in /properties of this project) into ${JBOSS_HOME}/modules/conf/main/properties

- Change the business.central.app.code property in login-module.properties to the correct application code to send to PWSecurity, for the purpose of retrieving user permissions for this instance of Business Central


## Restart and Test
- Restart JBoss EAP

- Ensure proper application deployment of Business Central

- Test authentication and authorization integration with PWSecurity
