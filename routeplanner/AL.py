from tokenize import ContStr
from turtle import distance
from helpers import load_map_test, load_map_bus, load_map_train,route_177,route_176,route_8717
import math

class PathPlanner():
   

    def __init__(self, M, start=None, goal=None, type=None):
        
        self.map = M
        self.start= start
        self.goal = goal
        self.type = type
        self.range = 0
        self.closedSet = self.create_closedSet() if goal != None and start != None else None
        self.openSet = self.create_openSet() if goal != None and start != None else None
        self.cameFrom = self.create_cameFrom() if goal != None and start != None else None
        self.gScore = self.create_gScore() if goal != None and start != None else None
        self.fScore = self.create_fScore() if goal != None and start != None else None
        self.path = self.run_search() if self.map and self.start != None and self.goal != None else None
    
    def reconstruct_path(self, current):
        total_path = [current]
        while current in self.cameFrom.keys():
            current = self.cameFrom[current]
            total_path.append(current)
        return total_path
    
    def _reset(self):
        self.closedSet = None
        self.openSet = None
        self.cameFrom = None
        self.gScore = None
        self.fScore = None
        self.path = self.run_search() if self.map and self.start and self.goal else None

    def run_search(self):
        """ """
        if self.map == None:
            raise(ValueError, "Must create map before running search")
        if self.goal == None:
            raise(ValueError, "Must create goal node before running search")
        if self.start == None:
            raise(ValueError, "Must create start node before running search")

        self.closedSet = self.closedSet if self.closedSet != None else self.create_closedSet()
        self.openSet = self.openSet if self.openSet != None else  self.create_openSet()
        self.cameFrom = self.cameFrom if self.cameFrom != None else  self.create_cameFrom()
        self.gScore = self.gScore if self.gScore != None else  self.create_gScore()
        self.fScore = self.fScore if self.fScore != None else  self.create_fScore()

        while not self.is_open_empty():
            current = self.get_current_node()

            if current == self.goal:
                self.path = [x for x in reversed(self.reconstruct_path(current))]
                return self.path
            else:
                self.openSet.remove(current)
                self.closedSet.add(current)

            for neighbor in self.get_neighbors(current):
                if neighbor in self.closedSet:
                    continue    # Ignore the neighbor which is already evaluated.

                if not neighbor in self.openSet:    # Discover a new node
                    self.openSet.add(neighbor)
                
                # The distance from start to a neighbor
                if self.getTempGScore(current, neighbor) >= self.get_gScore(neighbor):
                    continue 


                # This path is the best until now. Record it!
                
                self.record_best_path_to(current, neighbor)
                #self.range += self.dis

        print("No Path Found")
        self.path = None
        return False

    def create_closedSet(self):
        return set()

    def create_openSet(self):

        openSet = set()
        openSet.add(self.start)
        return openSet
        
    def create_cameFrom(self):
        cameFrom = {}
        return cameFrom

    def create_gScore(self):
        gScore = {}
        for node in self.map.intersections.keys():
            if node == self.start:
                gScore[node] = 0
            else: gScore[node] = float('inf')
        return gScore

    def create_fScore(self):
        if self.start != None:
            fScore = {}
            for node in self.map.intersections.keys():
                if node == self.start:
                    fScore[node] = self.heuristic_cost_estimate(self.start)
                else: fScore[node] = float('inf')
            return fScore
        raise(ValueError, "Must create start node before creating fScore.")

    def set_map(self, M):
        "set map attribute "
        self._reset(self)
        self.start = None
        self.goal = None 
        self.map = M

    def set_start(self, start):
        " set start attribute "
        self._reset(self)
        self.start = start
        self.goal = None
        
    def set_goal(self, goal):
        "set goal attribute "
        self._reset(self)
        self.goal = goal

    #-------------------------------- get information --------------------------------#

    def is_open_empty(self):
        "returns True if the open set is empty"
        return not bool(self.openSet)
    def get_current_node(self):
        " Returns the node in the open set with the lowest value of f(node)"
        
        current = None
        minim = float('inf')
        for node in self.openSet:
            if self.fScore[node] < minim:
                minim = self.fScore[node]
                current = node
        return current        

    def get_neighbors(self, node):
        """Returns the neighbors of a node"""
        return set(self.map.roads[node]) 

    #-------------------------------- Calculations --------------------------------#

    def get_gScore(self, node):
        return self.gScore[node]

    def getTempGScore(self, current, neighbor):
        # distance from the current node to it's neighbors
        
        g_score_current = self.get_gScore(current)
        dist_current_neighbor = self.distance(current,neighbor)
        return g_score_current+dist_current_neighbor

    def heuristic_cost_estimate(self, node):

        if self.goal != None:
            heuristic_estimate = self.distance(node,self.goal)
            return heuristic_estimate
        raise(ValueError, "Must create goal node before calculating huristic ")

    def calculate_fscore(self, node):
        #  F = G + H
        
        f_score = self.get_gScore(node) + self.heuristic_cost_estimate(node)
        return f_score


    # best path
    def record_best_path_to(self, current, neighbor):
        
        self.cameFrom[neighbor] = current
        self.gScore[neighbor] = self.getTempGScore(current,neighbor)
        self.fScore[neighbor] = self.gScore[neighbor] + self.heuristic_cost_estimate(neighbor)

    def distance(self, node_1, node_2):
        from math import radians, cos, sin, asin, sqrt

        #The math module contains a function named
        #radians which converts from degrees to radians.

        lon1 = radians(self.map.intersections [node_1][0])
        lon2 = radians(self.map.intersections [node_2][0])
        lat1 = radians(self.map.intersections [node_1][1])
        lat2 = radians(self.map.intersections [node_2][1])
        
        # Haversine formula
        dlon = lon2 - lon1
        dlat = lat2 - lat1
        a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
    
        c = 2 * asin(sqrt(a))
        
        # Radius of earth in kilometers. Use 3956 for miles
        r = 6371
        
        # calculate the result
        dist = (c * r)
        #print (dist)
        #self.dis = dist
        return dist

    def get_traveled_distance(self):

        #traveled_distance 
        lastNode = self.start
        for n in self.path[1:]:
            self.range = self.range+ distance(lastNode, n )
            lastNode = n   

        return self.range

    def get_fare(self,path):
        cost = 0
        currentRouteNo = self.map.routeNo[self.start]

        temp = 0
        for x in path:
            if self.map.routeNo[x] == currentRouteNo:
                print('path is(x) =' + str(x)+ ' ,temp =' + str(temp) + ' ,cost =' + str(cost))
                temp+=1 
            else:
                if(currentRouteNo == 177):
                    cost = cost + route_177.get(temp)
                    temp = 0
                    currentRouteNo = self.map.routeNo[x]
                elif(currentRouteNo == 176):
                    cost = cost + route_176.get(temp)
                    temp = 0
                    currentRouteNo = self.map.routeNo[x]
                elif(currentRouteNo == 8717):
                    cost = cost + route_8717.get(temp)
                    temp = 0
                    currentRouteNo = self.map.routeNo[x]
                else:
                    temp = 0
        cost = cost + route_177.get(temp)
        return cost

# Get route  
def main( a, start, destination):
    if a == 3:
        map = load_map_train()
    elif a == 2:
        map = load_map_bus()
    else:
        map = load_map_test()
    
    planner = PathPlanner(map,start,destination)
    print(map.roads)

    path = planner.path
    
    print("Genarated path")
    if path == False:
        print("No path Found", path)
        return
    else: 
        print(path)
        print('distance =' , planner.get_traveled_distance())
        #print(planner.get_fare(path))

        

        

            


main( 1, 0, 3)   