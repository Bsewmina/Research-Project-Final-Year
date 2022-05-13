
import networkx as nx
import pickle
from chart_studio import plotly as py
#import plotly.plotly as py
import random
from plotly.graph_objs import *
from plotly.offline import init_notebook_mode, plot, iplot
init_notebook_mode(connected=True)


map_10_dict = {
	0: {'pos': (0.7798606835438107, 0.6922727646627362), 'connections': [7, 6, 5]}, 
	1: {'pos': (0.7647837074641568, 0.3252670836724646), 'connections': [4, 3, 2]}, 
	2: {'pos': (0.7155217893995438, 0.20026498027300055), 'connections': [4, 3, 1]}, 
	3: {'pos': (0.7076566826610747, 0.3278339270610988), 'connections': [5, 4, 1, 2]}, 
	4: {'pos': (0.8325506249953353, 0.02310946309985762), 'connections': [1, 2, 3]}, 
	5: {'pos': (0.49016747075266875, 0.5464878695400415), 'connections': [7, 0, 3]}, 
	6: {'pos': (0.8820353070895344, 0.6791919587749445), 'connections': [0]}, 
	7: {'pos': (0.46247219371675075, 0.6258061621642713), 'connections': [0, 5]}, 
	8: {'pos': (0.11622158839385677, 0.11236327488812581), 'connections': [9]}, 
	9: {'pos': (0.1285377678230034, 0.3285840695698353), 'connections': [8]}
}


class Map:
	def __init__(self, G):
		self._graph = G
		self.intersections = nx.get_node_attributes(G, "pos")
		self.roads = [list(G[node]) for node in G.nodes()]

	def save(self, filename):
		with open(filename, 'wb') as f:
			pickle.dump(self._graph, f)

def load_map_graph(map_dict):
	G = nx.Graph()
	for node in map_dict.keys():
		G.add_node(node, pos=map_dict[node]['pos'])
	for node in map_dict.keys():
		for con_node in map_dict[node]['connections']:
			G.add_edge(node, con_node)
	return G

def load_map_10():
	G = load_map_graph(map_10_dict)
	return Map(G)

def load_map_40():
	G = load_map_graph(map_40_dict)
	return Map(G)

def show_map(M, start=None, goal=None, path=None):
    G = M._graph
    pos = nx.get_node_attributes(G, 'pos')
    edge_trace = Scatter(
    x=[],
    y=[],
    line=Line(width=0.5,color='#888'),
    hoverinfo='none',
    mode='lines')

    for edge in G.edges():
        x0, y0 = G.node[edge[0]]['pos']
        x1, y1 = G.node[edge[1]]['pos']
        edge_trace['x'] += [x0, x1, None]
        edge_trace['y'] += [y0, y1, None]

    node_trace = Scatter(
        x=[],
        y=[],
        text=[],
        mode='markers',
        hoverinfo='text',
        marker=Marker(
            showscale=False,
            # colorscale options
            # 'Greys' | 'Greens' | 'Bluered' | 'Hot' | 'Picnic' | 'Portland' |
            # Jet' | 'RdBu' | 'Blackbody' | 'Earth' | 'Electric' | 'YIOrRd' | 'YIGnBu'
            colorscale='Hot',
            reversescale=True,
            color=[],
            size=10,
            colorbar=dict(
                thickness=15,
                title='Node Connections',
                xanchor='left',
                titleside='right'
            ),
            line=dict(width=2)))
    for node in G.nodes():
        x, y = G.node[node]['pos']
        node_trace['x'].append(x)
        node_trace['y'].append(y)

    for node, adjacencies in enumerate(G.adjacency_list()):
        color = 0
        if path and node in path:
            color = 2
        if node == start:
            color = 3
        elif node == goal:
            color = 1
        # node_trace['marker']['color'].append(len(adjacencies))
        node_trace['marker']['color'].append(color)
        node_info = "Intersection " + str(node)
        node_trace['text'].append(node_info)

    fig = Figure(data=Data([edge_trace, node_trace]),
                 layout=Layout(
                    title='<br>Network graph made with Python',
                    titlefont=dict(size=16),
                    showlegend=False,
                    hovermode='closest',
                    margin=dict(b=20,l=5,r=5,t=40),
                   
                    xaxis=XAxis(showgrid=False, zeroline=False, showticklabels=False),
                    yaxis=YAxis(showgrid=False, zeroline=False, showticklabels=False)))

    iplot(fig)
