# vote-electronique-rmi 

# L'objectif 
   L'objectif est de créer un système de vote électronique, nous mettons également en œuvre le concept de signature électronique 
    afin de vérifier l'identité de l'électeur, ce système sera basé sur RMI (Remote method invocation). 
    
# Les fonctionnalités
 Tout d'abord lorsque nous démarrons le serveur, vous devez spécifier le [nom de l'élection avec la date de début et de fin du vote](#initserver), Ce serveur utilise la méthode "init" pour saisir les candidats et aussi il  proposera plusieurs méthodes :<br/> 
      1 :[ register](#register), pour qu'un  électeur s'inscrit dans l'élection avant le début du vote  <br/>
      2:  [vote](#vote)   , pour voter <br/>
      3: [changeVote](#changeVote) ,pour changé le vote 4: getCandidats <br/>
      5: [getResults](#getResults)<br/>
  Concernant le client, tout d'abord, il faut créer une [keyPair](#keyPair) pour qu'il puisse utiliser les méthodes du serveur.<br/>


# Test
<a name="initserver" ></a>
  ![plot](./screen/initserver.png)
  
  
  <a name="keyPair" ></a>
  ![plot](./screen/keyPair.png)
  
 <a name="register" ></a>
  ![plot](./screen/inscription.png)
  
  <a name="vote" ></a>
  ![plot](./screen/vote.png)
  
  <a name="changeVote" ></a>
  ![plot](./screen/changevote.png)
  
  <a name="getResults" ></a>
  ![plot](./screen/rs.png)
  
  
