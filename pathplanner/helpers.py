
import networkx as nx
from chart_studio import plotly as py
import random
#init_notebook_mode(connected=True)


route_177 = { 
	
	0:0,
	1: 34, 
	2: 42, 
	3: 54, 
	4: 67, 
	5: 80, 
	6: 92, 
	7: 105, 
	8: 109, 
	9: 117,
	10: 126,
	11: 134,
	12: 140,
	13: 149,
	14: 157,
	15: 163,
}

route_176 = { 

	0:0,
	1: 34, 
	2: 42, 
	3: 54, 
}

route_8717 = {  

	0:0,
	1: 20, 
	2: 20, 
	3: 20, 
	4: 20,
	5: 40
}

map_test = {
	0: {'pos': (6.911034627182109, 79.84918916006576), 'connections': [1,16], 'name' : 'Kollupitiya','type': 0,'routeNo': 177, 'hValue': 5}, 
	1: {'pos': (6.911751932322411, 79.86194701315071), 'connections': [0,2], 'name' : 'Viharamahadevi Park', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	2: {'pos': (6.911385550864001, 79.87682791026592), 'connections': [1,3], 'name' : 'House Of Fashion', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	3: {'pos': (6.911031363415147, 79.88498429384545), 'connections': [2,4], 'name' : 'Castle Street', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	4: {'pos': (6.908462881966912, 79.89338919261249), 'connections': [3,5], 'name' : 'Rajagiriya', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	5: {'pos': (6.906663916178293, 79.90205413217801), 'connections': [4,6], 'name' : 'HSBC Rajagiriya', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	6: {'pos': (6.90333333857459, 79.90747047529919), 'connections': [5,7], 'name' : 'Ethulkotte New', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	7: {'pos': (6.903185701293392, 79.91168232658337), 'connections': [6,8], 'name' : 'Parliament Junction', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	8: {'pos': (6.902102452411621, 79.91741047710077), 'connections': [7,9], 'name' : 'Battaramulla Junction', 'type': 0, 'routeNo': 177, 'hValue': 1}, 
	9: {'pos': (6.90431022366131, 79.92400480782847), 'connections': [8,10], 'name' : 'Ganahena', 'type': 0, 'routeNo': 177, 'hValue': 1},
	10: {'pos': (6.906173952520986, 79.92862087153598), 'connections': [9,11], 'name' : 'Koswatta', 'type': 0, 'routeNo': 177, 'hValue': 1},
	11: {'pos': (6.9084657304543455, 79.9383708894408), 'connections': [10,12], 'name' : 'Kotte-Bope', 'type': 0, 'routeNo': 177, 'hValue': 1},
	12: {'pos': (6.9085023402918155, 79.94425286198016), 'connections': [11,13], 'name' : 'Thalahena Junction', 'type': 0, 'routeNo': 177, 'hValue': 1},
	13: {'pos': (6.903962700873259, 79.95430199947661), 'connections': [12,14], 'name' : 'Malabe', 'type': 0, 'routeNo': 177, 'hValue': 1},
	14: {'pos': (6.933539686667202, 79.85005389798111), 'connections': [15], 'name' : 'Fort_TR', 'type': 1, 'routeNo': 8717, 'hValue': 1},
	15: {'pos': (6.923619774894732, 79.84965509086771), 'connections': [14,16], 'name' : 'Kompannavidiya_TR', 'type': 1, 'routeNo': 8717, 'hValue': 1},
	16: {'pos': (6.911282166657041, 79.84821747831087), 'connections': [15,1,17], 'name' : 'Kollupitiya_TR', 'type': 1, 'routeNo': 8717, 'hValue': 1},
	17: {'pos': (6.893710110662125, 79.85300590028642), 'connections': [16,18], 'name' : 'Bambalapitiya_TR', 'type': 1, 'routeNo': 8717, 'hValue': 1},
	18: {'pos': (6.875030063532378, 79.85744793142631), 'connections': [17,19], 'name' : 'Wellawatte_TR', 'type': 1, 'routeNo': 8717, 'hValue': 1},
	19: {'pos': (6.850915755796403, 79.8620969312958), 'connections': [18], 'name' : 'Dehiwala_TR', 'type': 1, 'routeNo': 8717, 'hValue': 1},
}


class Map:
	def __init__(self, G):
		self._graph = G
		self.intersections = nx.get_node_attributes(G, "pos")
		self.routeNo = nx.get_node_attributes(G, "routeNo")
		self.type = nx.get_node_attributes(G, "type")
		self.name = nx.get_node_attributes(G, "name")
		self.roads = [list(G[node]) for node in G.nodes()]

def load_map_graph(map_dict):
	G = nx.Graph()
	for node in map_dict.keys():
		G.add_node(node, pos=map_dict[node]['pos'], type=map_dict[node]['type'],routeNo=map_dict[node]['routeNo'],name=map_dict[node]['name'])
	for node in map_dict.keys():
		for con_node in map_dict[node]['connections']:
			G.add_edge(node, con_node)
	
	return G
	

def load_map_b(map_dict):
	
	G = nx.Graph()
	for node in map_dict.keys():
		G.add_node(node, pos=map_dict[node]['pos'], type=map_dict[node]['type'],routeNo=map_dict[node]['routeNo'])	

	for node in map_dict.keys():
		for con_node in map_dict[node]['connections']:
			if map_dict[node]['type'] != 1:
				continue
			elif map_dict[con_node]['type'] != 1:
				continue
			else:
				G.add_edge(node, con_node)
	return G

#map for train
def load_map_t(map_dict):
	G = nx.Graph()
	for node in map_dict.keys():
		G.add_node(node, pos=map_dict[node]['pos'], type=map_dict[node]['type'],routeNo=map_dict[node]['routeNo'])	
	
	for node in map_dict.keys():
		for con_node in map_dict[node]['connections']:
			if map_dict[node]['type'] == 1:
				continue
			elif map_dict[con_node]['type'] == 1:
				continue
			else:
				G.add_edge(node, con_node)
	return G

def load_map_test():
	G = load_map_graph(map_test)
	return Map(G)

def load_map_bus():
	G = load_map_b(map_test)
	return Map(G)

def load_map_train():
	G = load_map_t(map_test)
	return Map(G)

