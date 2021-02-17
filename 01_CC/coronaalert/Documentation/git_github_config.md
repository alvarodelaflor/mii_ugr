# Creation of SSH public/private key pair

The following images are shown to justify its creation.

First of all, the key must be created:

![Sample password created](https://raw.githubusercontent.com/alvarodelaflor/CC-Proyecto/master/Documentation/images/create_key.png)

Secondly, this key must be added to GitHub:

![Add the key to GitHub](https://raw.githubusercontent.com/alvarodelaflor/CC-Proyecto/master/Documentation/images/add_key.png)

# Creation of repositories for the subject: self-evaluation exercises, project and fork of the subject

Three repositories have been created:

    1. https://github.com/alvarodelaflor/CC-20-21/
    2. https://github.com/alvarodelaflor/CC-Ejercicios/
    3. https://github.com/alvarodelaflor/CC-Proyecto/

# Configuration of the correct remotes for repository CC-20-21

To perform this step you simply need to make this configuration in the project:

- git remote add upstream https://github.com/JJ/CC-20-21.git

# Correct local git settings: name, email address, override settings

The following screenshot shows the configuration of the name, mail and the setting of the default pull type.

![Configuración de GitHub](https://raw.githubusercontent.com/alvarodelaflor/CC-Proyecto/master/Documentation/images/git_settings.png)

# Second authentication factor

The following two images show how the second authentication factor has been activated, in this case by sending an SMS message to a personal number.

![Auth1](https://raw.githubusercontent.com/alvarodelaflor/CC-Proyecto/master/Documentation/images/security_1.png)
![Auth2](https://raw.githubusercontent.com/alvarodelaflor/CC-Proyecto/master/Documentation/images/security_2.png)

# Issues Configuration

A guide of good practices will then be written regarding the configuration of the issues to be used throughout this project.

- Avoiding repeated issues.
- Use a sufficiently descriptive title.
- Explain the problem and the expected result.
- Each commit must be associated with an issue, so once it is completed it must be closed.

Finally, each issue must be associated with a milestone and a label corresponding to the type of problem it refers to.