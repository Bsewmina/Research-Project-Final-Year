from helpers import load_map_train, load_map_test, load_map_bus

ANSWERS = [
    (1,5, 10, [5, 6, 7, 8, 9,10]),
    (1,5, 5,  [5]),
    (1,3, 18, [3, 2, 1, 16, 17, 18]),
    (1,14, 18,  [14, 15, 16, 17, 18]),
    (1,15, 2,  [15, 16, 1, 2])
]


def test(path_planner_function):
    print('testing started ---------------')
    correct = 0
    for mapval, start, goal, answer_path in ANSWERS:
        if mapval == 3:
            map = load_map_train()
        elif mapval == 2:
            map = load_map_bus()
        else:
            map = load_map_test()
            
        path = path_planner_function(map, start, goal).path
        if path == answer_path:
            correct += 1
            print("For start:", start, 
                  "Goal:     ", goal,
                  "Your path:", path,
                  "Correct:  ", answer_path)
        else:
             print("Error Testing faild !!!" )
    if correct == len(ANSWERS):
        print("All tests pass ")
    else:
        print("Only passed", correct, "/", len(ANSWERS), "test cases")
    