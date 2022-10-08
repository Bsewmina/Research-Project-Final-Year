from helpers import load_map_test

MAP_ANSWERS = [
    (5, 34, [5, 16, 37, 12, 34]),
    (5, 5,  [5]),
    (8, 24, [8, 14, 16, 37, 12, 17, 10, 24])
]

def test(shortest_path_function):
    map = load_map_test()
    correct = 0
    for start, goal, answer_path in MAP_ANSWERS:
        path = shortest_path_function(map, start, goal).path
        if path == answer_path:
            correct += 1
            print("For start:", start, 
                  "Goal:     ", goal,
                  "Your path:", path,
                  "Correct:  ", answer_path)
        else:
             print("Error Testing faild !!!" )
    if correct == len(MAP_ANSWERS):
        print("All tests pass ")
    else:
        print("Only passed", correct, "/", len(MAP_ANSWERS), "test cases")
    