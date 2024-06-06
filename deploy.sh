echo -e "\033[33m     .___  ___.      ___       __    __   __   __  ___      ___      \033[0m";
echo -e "\033[33m     |   \/   |     /   \     |  |  |  | |  | |  |/  /     /   \     \033[0m";
echo -e "\033[33m     |  \  /  |    /  ^  \    |  |__|  | |  | |  '  /     /  ^  \    \033[0m";
echo -e "\033[33m     |  |\/|  |   /  /_\  \   |   __   | |  | |    <     /  /_\  \   \033[0m";
echo -e "\033[33m     |  |  |  |  /  _____  \  |  |  |  | |  | |  .  \   /  _____  \  \033[0m";
echo -e "\033[33m     |__|  |__| /__/     \__\ |__|  |__| |__| |__|\__\ /__/     \__\ \033[0m";
echo " ";
echo -e "\033[94m Building WAR file...\033[0m"
./mvnw clean package
echo -e "\033[94m Shut down tomcat...\033[0m"
ssh root@64.227.1.44 /opt/tomcat/bin/shutdown.sh
echo -e "\033[94m Delete old file in webapps folder...\033[0m"
ssh root@64.227.1.44 rm -rf /opt/tomcat/webapps/*.war
echo -e "\033[94m Deploy WAR file to webapps folder...\033[0m"
scp -r -i ~/.ssh/id_rsa target/swp.war root@64.227.1.44:/opt/tomcat/webapps/
echo -e "\033[94m Boost up tomcat...\033[0m"
ssh root@64.227.1.44 /opt/tomcat/bin/startup.sh
echo -e "\033[94m Done!\033[0m"