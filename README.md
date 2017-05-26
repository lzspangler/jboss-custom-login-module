Custom UsersRolesLoginModule for EAP 7
========================================

Setup
-------
1. Clone repository and add necessary dependency for the PWSecurity classes to the pom

2. Run 'mvn clean install' to compile 

3. Copy the /target/jboss-custom-login.jar to ${JBOSS_HOME}/standalone/deployments/business-central.war/WEB-INF/lib

3. Update ${JBOSS_HOME}/standalone/configuration/standalone.xml to define the security domain by including the following in `<security-domains>`:

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
In the above be sure to change <path to rolemapping file> to the actual path where the rolemapping-roles.properties will be placed


4. Enter the proper PWSecurity permission code to Business Central role and group mappings in the rolemapping-roles.properties and copy it to the location corresponding to the standalone config


5. Restart JBoss EAP