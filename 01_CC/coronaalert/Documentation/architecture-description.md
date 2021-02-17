
# **Architecture of the problem** :construction:

Before proposing the architecture to be used in this project, we will focus on the problem we are trying to solve by adjusting it to the environment in which it is intended to be used (with special emphasis on the cloud) and from this, achieve a series of defined characteristics that will consequently determine the use of one architecture or another.

## **Problem**

As defined in the [description of the problem](description.md), the final objective of this project is very simple, to alert a user of the *ETSIIT* as soon as possible that there is a possibility that he has maintained a close contact with a positive case of *COVID-19*, being necessary to take the control of attendance of all the users of this building.

## **Features**

1. **Cloud service**

After investigating the problem I am trying to solve I have been able to verify that there are many solutions based on physical devices or where a lot of human interaction is required.

The goal of this project is to achieve a product that minimizes the direct active management by users. In other words, to achieve a high degree of automation and capacity for changes and that is capable of being deployed in the cloud.

1. **Modularity and scalability**

Obviously, continuing with the previous section, it is logical that nowadays it is very important to take into account the costs. Cloud services can be very expensive and as a result, the allocation of resources must be taken into account.

The idea of this project is to divide the functionality offered into clearly separated modules. The architecture that is finally chosen must be able to take advantage of this modularity, in the sense of allowing the project to scale and distribute resources in a clearly defined way depending on the needs of that particular situation.

That is, to have the maximum possible efficiency from the resources that are available.

## **Candidates**

In short, we need an architecture that facilitates:
    
1. Great compatibility with the cloud
2. Automation
3. Tolerance to changes
4. Modularity
5. Scalability

If we focus on these sections, we can make a great first choice which is:

1. Architecture based on microservices
2. Event-driven architecture

The main reason to rule out other types of architectures like *layer-based architecture*, *microkernel architecture*... is their problem with scalability. Clearly, as we have mentioned before, scalability and modularity is one of the main points required and these two architectures are the best suited for this scalability.

So much importance is given to scalability since the goal of any application is to grow. Although at the beginning the service offered by this project is very basic and only one university will be managed, it may be the case that this application will extend much further and scalability will become a basic and necessary block.

## **Choice**

First of all, as a personal opinion, I think that for the project described, an architecture based on microservices is better adapted to the problem we want to solve, based on the atomicity of the functionality we want to achieve. 

Furthermore, thanks to the characteristics of the microservices, they can be easily reused, and in a context like this, in which one of the functions is attendance control for a public body, great opportunities are created for this reuse, for example exams, worker control, even laboratory material for equipment use control.

Event driven architecture, in my opinion, is more suitable for services with special interest in real time responses.

One point that I consider quite important is the difficulty. Both architectures are completely unknown to me, new paradigms. Assuming the difficulty that can come to suppose the election of *the architecture based on events* and adding that personally I think that *the architecture based on microservices* adapts better to this project.

Finally I have decided that for this project **the architecture based on microservices will be used**.