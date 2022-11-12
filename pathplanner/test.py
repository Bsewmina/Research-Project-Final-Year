
ANSWERS = [
    (1,5, 34, [5, 16, 37, 12, 34]),
    (1,5, 5,  [5]),
    (1,8, 24, [8, 14, 16, 37, 12, 17, 10, 24])
]

def test(shortest_path_function):
    print('test started ---------------')
    correct = 0
    for map, start, goal, answer_path in ANSWERS:
        path = shortest_path_function(map, start, goal).path
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
    