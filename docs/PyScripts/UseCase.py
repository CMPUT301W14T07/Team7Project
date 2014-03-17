def inputs(str):
    return raw_input(str).strip()

# Provide example of a use case (from lecture slides)
def intro():
    print("[Example use case from lecture slides]")
    print("Name: SearchForNutritionInfo")
    print("Actors: Meal Planner")
    print("Goals: Meal Planner finds nutrition information")
    print("Trigger: Meal Planner chooses the Search option")
    print("Precondition: Meal Planner knows food name and amount")
    print("Postcondition: On success, nutrition information is displayed\n\n")

# Takes inputs for one use case
# APPENDS new markup for each use case to UseCases.txt
def mainfunc():        
    name = ""
    actors = ""
    goal = ""
    trigger = ""
    precond = ""
    postcond = ""

    while True:
        name = inputs("Name: ")
        actors = inputs("Actors: ")
        goal = inputs("Goal: ")
        trigger = inputs("Trigger: ")
        precond = inputs("Precond: ")
        postcond = inputs("Postcond: ")
        
        outFile = open("./UseCases.txt", "a")
        outFile.write("|ID: | |" + "\n")
        outFile.write("|---|---|" + "\n")
        outFile.write("Name| **" + name + "**\n")
        outFile.write("Actors| " + actors + "\n")
        outFile.write("Goal| " + goal + "\n")
        outFile.write("Trigger| " + trigger + "\n")
        outFile.write("Precondition| " + precond + "\n")
        outFile.write("Postcondition| " + postcond + "\n" + "\n")
        outFile.close()
        
        print("\n")

if __name__ == "__main__":
    intro()
    mainfunc()