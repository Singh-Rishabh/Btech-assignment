# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
      A reflex agent chooses an action at each choice point by examining
      its alternatives via a state evaluation function.

      The code below is provided as a guide.  You are welcome to change
      it in any way you see fit, so long as you don't touch our method
      headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {North, South, West, East, Stop}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)         # Variable to store sucessor game state
        newPos = successorGameState.getPacmanPosition()                               # Variable to store current position of Pacman
        newFood = currentGameState.getFood()                                          # Variable to store food position 
        newGhostStates = successorGameState.getGhostStates()                          # Variable to store ghost posotion
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]    # Variable to store scared time of ghosts

        "*** YOUR CODE HERE ***"

        '''
          Calculated minimum distance of food from pacman position
          then calculated minimum distacne of ghost from pacman position
          take their recripocal and added them to give output result.
        '''

        ret_val = 0
        food_arr = newFood.asList()
        min_dis_food = 999999
        min_ind_food = -1
        for i in range(len(food_arr)):
          temp = util.manhattanDistance(newPos,food_arr[i])
          if (temp < min_dis_food):
            min_dis_food = temp
            min_ind_food = i

        min_dis = 999999
        min_ind = -1
        i = 0
        for ghost in newGhostStates:
          temp = util.manhattanDistance(newPos,ghost.getPosition())
          if (temp < min_dis):
            min_dis = temp
            min_ind = i
          i += 1
        if (min_dis == 0):
          return -1
        if (min_dis_food == 0):
          return 1
        ret_val = 1.0/util.manhattanDistance(newPos, food_arr[min_ind_food]) - 1.0/util.manhattanDistance(newPos,newGhostStates[min_ind].getPosition())
        return ret_val

def scoreEvaluationFunction(currentGameState):
    """
      This default evaluation function just returns the score of the state.
      The score is the same one displayed in the Pacman GUI.

      This evaluation function is meant for use with adversarial search agents
      (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
      This class provides some common elements to all of your
      multi-agent searchers.  Any methods defined here will be available
      to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

      You *do not* need to make any changes here, but you can if you want to
      add functionality to all your adversarial search agents.  Please do not
      remove anything, however.

      Note: this is an abstract class: one that should not be instantiated.  It's
      only partially specified, and designed to be extended.  Agent (game.py)
      is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
      Your minimax agent (question 2)
    """

    # function to calculate the min_Agent output values
    def min_agent(self, gameState ,index ):
      n = gameState.getNumAgents()
      legalMoves = gameState.getLegalActions(index%n)
      # print(legalMoves)
      if (len(legalMoves) == 0):
        return (self.evaluationFunction(gameState), None)
      min_score = 999987
      min_move = None
      n = gameState.getNumAgents()
      for move in legalMoves:
        if (move == 'Stop'):
          continue
        new_socre,new_move = self.minmax(gameState.generateSuccessor(index%n,move),index + 1)
        if (new_socre < min_score):
          min_score = new_socre
          min_move = move
      # print("min_score " + str(min_score))
      return (min_score, min_move)

    # function to calculate the max_agent output values
    def max_agent(self ,gameState ,index):
      legalMoves = gameState.getLegalActions()
      n = gameState.getNumAgents()
      if (len(legalMoves) == 0):
        return (self.evaluationFunction(gameState), None)
      max_score = -999999
      max_move = None
      for move in legalMoves:
        if (move == 'Stop'):
          continue
        new_socre,new_move = self.minmax(gameState.generateSuccessor(index%n,move),index + 1)
        if (new_socre > max_score):
          max_score = new_socre
          max_move = move
      return (max_score, max_move)


    # function to decide which of agent turn it is and call that agent to give iutput.
    def minmax(self , gameState, index):
      n = gameState.getNumAgents()
      if (self.depth*n == index or gameState.isWin() or gameState.isLose()):
        return (self.evaluationFunction(gameState), None)
      if (index%n == 0):
        return self.max_agent(gameState,index)
      else:
        return self.min_agent(gameState, index)

    def getAction(self, gameState):
        """
          Returns the minimax action from the current gameState using self.depth
          and self.evaluationFunction.

          Here are some method calls that might be useful when implementing minimax.

          gameState.getLegalActions(agentIndex):
            Returns a list of legal actions for an agent
            agentIndex=0 means Pacman, ghosts are >= 1

          gameState.generateSuccessor(agentIndex, action):
            Returns the successor game state after an agent takes an action

          gameState.getNumAgents():
            Returns the total number of agents in the game
        """
        "*** YOUR CODE HERE ***"

        ''' 
            every agent with index 0 is pacman and 
            every agent with index > 0 is a ghost so
            I created an index variable and passed it 
            to minimax function to get the action which
            maximizes the pacman output.

        '''

        index = 0
        bestAction = self.minmax(gameState, index)
        return bestAction[1]




class AlphaBetaAgent(MultiAgentSearchAgent):
    """
      Your minimax agent with alpha-beta pruning (question 3)
    """

    # similar to function min_agent in minimax algo
    # except it also checks condition if beta < alpha
    # to prune tree.
    def min_agent(self, gameState ,index,alpha , beta ):
      n = gameState.getNumAgents()
      legalMoves = gameState.getLegalActions(index%n)
      # print(legalMoves)
      if (len(legalMoves) == 0):
        return (self.evaluationFunction(gameState), None)
      min_score = 999987
      min_move = None
      n = gameState.getNumAgents()
      for move in legalMoves:
        if (move == 'Stop'):
          continue
        new_socre,new_move = self.alphaBeta(gameState.generateSuccessor(index%n,move),index + 1, alpha,beta)

        if (new_socre < min_score):
          min_score = new_socre
          min_move = move
        beta = min(beta,min_score)
        if beta < alpha:
          break
      # print("min_score " + str(min_score))
      return (min_score, min_move)

    # similar to function max_agent in minimax algo
    # except it also checks condition if beta < alpha
    # to prune tree.
    def max_agent(self ,gameState ,index, alpha , beta):
      legalMoves = gameState.getLegalActions()
      n = gameState.getNumAgents()
      # print (legalMoves)
      if (len(legalMoves) == 0):
        return (self.evaluationFunction(gameState), None)
      max_score = -999999
      max_move = None
      for move in legalMoves:
        if (move == 'Stop'):
          continue
        new_socre,new_move = self.alphaBeta(gameState.generateSuccessor(index%n,move),index + 1, alpha, beta)
        if (new_socre > max_score):
          max_score = new_socre
          max_move = move
        alpha = max(alpha,max_score)
        if beta < alpha:
          break
      # print("max_score " + str(max_score))
      return (max_score, max_move)

    # similar to function minimax
    def alphaBeta(self , gameState, index, alpha, beta):
      n = gameState.getNumAgents()
      if (self.depth*n == index or gameState.isWin() or gameState.isLose()):
        return (self.evaluationFunction(gameState), None)
      if (index%n == 0):
        return self.max_agent(gameState,index, alpha, beta)
      else:
        return self.min_agent(gameState, index, alpha, beta)

    def getAction(self, gameState):
        """
          Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"

        ''' 
            every agent with index 0 is pacman and 
            every agent with index > 0 is a ghost so
            I created an index variable and passed it 
            to minimax function to get the action which
            maximizes the pacman output.

        '''
        index = 0
        alpha = -999999
        beta = 999999
        bestAction = self.alphaBeta(gameState, index, alpha, beta)
        return bestAction[1]



class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    # function to calculate expected min_value of the
    # min_agent
    def min_agent(self, gameState ,index ):
      n = gameState.getNumAgents()
      legalMoves = gameState.getLegalActions(index%n)
      # print(legalMoves)
      if (len(legalMoves) == 0):
        return (self.evaluationFunction(gameState), None)
      min_score = 999987
      min_move = None
      n = gameState.getNumAgents()
      avg_score = 0.0
      count = 0.0
      for move in legalMoves:
        if (move == 'Stop'):
          continue
        new_socre,new_move = self.ExpectedMinMax(gameState.generateSuccessor(index%n,move),index + 1)
        count += 1
        avg_score += new_socre
        if (new_socre < min_score):
          min_score = new_socre
          min_move = move
      # print("min_score " + str(min_score))
      return (avg_score/count, min_move)

    # function to calculate max value of max_agent
    def max_agent(self ,gameState ,index):
      legalMoves = gameState.getLegalActions()
      n = gameState.getNumAgents()
      # print (legalMoves)
      if (len(legalMoves) == 0):
        return (self.evaluationFunction(gameState), None)
      max_score = -999999
      max_move = None
      
      for move in legalMoves:
        if (move == 'Stop'):
          continue
        new_socre,new_move = self.ExpectedMinMax(gameState.generateSuccessor(index%n,move),index + 1)
        
        if (new_socre > max_score):
          max_score = new_socre
          max_move = move
      # print("max_score " + str(max_score))
      return (max_score, max_move)

    # ExpectedMinimax function
    def ExpectedMinMax(self , gameState, index):
      n = gameState.getNumAgents()
      if (self.depth*n == index or gameState.isWin() or gameState.isLose()):
        return (self.evaluationFunction(gameState), None)
      if (index%n == 0):
        return self.max_agent(gameState,index)
      else:
        return self.min_agent(gameState, index)


    def getAction(self, gameState):
        """
          Returns the expectimax action using self.depth and self.evaluationFunction

          All ghosts should be modeled as choosing uniformly at random from their
          legal moves.
        """
        "*** YOUR CODE HERE ***"
        index = 0
        bestAction = self.max_agent(gameState, index)
        return bestAction[1]


    
def betterEvaluationFunction(currentGameState):
    """
      Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
      evaluation function (question 5).

      DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"

    '''
        Calculated total distance of food from pacman position
        then Calculated total distance of capsules from pacman position
        then calculated total distacne of ghost from pacman position
        take their recripocal and added them with proper weights to give output result.
    '''

    currentPos = currentGameState.getPacmanPosition()
    currentFood = currentGameState.getFood().asList()
    currentCapsules = currentGameState.getCapsules()
    currentGhostStates = currentGameState.getGhostStates()
    currentGhostStates_arr = [ghost.getPosition() for ghost in currentGhostStates]
    newScaredTimes = [ghostState.scaredTimer for ghostState in currentGhostStates]
    total_food_distance = 0
    count = 0
    min_food_dis = 999999
    min_index = -1
    for i in currentFood:
      total_food_distance += util.manhattanDistance(i,currentPos)
      if (min_food_dis > util.manhattanDistance(i,currentPos)):
        min_food_dis = util.manhattanDistance(i,currentPos)
        min_index = count
      count +=1

    min_capsule_distance = 999999
    total_capsule_distance = 0
    count = 0
    for i in currentCapsules:
      temp = util.manhattanDistance(i,currentPos)
      total_capsule_distance += temp
      if (temp < min_capsule_distance ):
        min_capsule_distance = temp
        min_capsule_index = count
      count += 1

    # print (min_capsule_distance)
    min_ghost_distance = 999999
    min_ghost_index = -1
    count = 0
    total_ghost_distance = 0
    for ghost in currentGhostStates:
      temp = util.manhattanDistance(currentPos,ghost.getPosition())
      total_ghost_distance += temp
      if (temp < min_ghost_distance):
        min_ghost_distance = temp
        min_ghost_index = count
      count += 1

    total_scared_time = sum(newScaredTimes)
    
    if (min_ghost_distance == 0):
      return -100
    if (total_food_distance == 0):
      return 200
    if (total_capsule_distance == 0):
      total_capsule_distance = 1
    if (total_ghost_distance == 0):
      total_ghost_distance = 1
    current_score = currentGameState.getScore()
    ret_val = 4.0/total_food_distance  + 2.0/total_capsule_distance - 0.5/total_ghost_distance
    
    
    return ret_val

# Abbreviation
better = betterEvaluationFunction

