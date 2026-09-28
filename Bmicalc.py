# ================================================================
# BMI Calculator (Beginner Version)
# Author: Renz A. General
#
# What this program does:
#   1. Asks for your height and weight
#   2. Calculates your BMI
#   3. Tells you what category you're in
#   4. Remembers your past calculations
#
# Run it with:  python bmicalc.py
# ================================================================

# ---- Import the tools we need ----
# "tkinter" makes windows, buttons, and text boxes
import tkinter as tk

# "messagebox" makes popup alerts (like "Invalid input!")
from tkinter import messagebox


# ================================================================
# PART 1: OUR MEMORY
# ================================================================
# This list stores every calculation we do.
# It starts empty. Each time we calculate, we add one line.
history = []


# ================================================================
# PART 2: THE MAIN BUTTON — Calculate BMI
# ================================================================
def calculate_bmi():
    # ---- Step 1: Get what the user typed ----
    height_text = height_box.get()   # read the height text box
    weight_text = weight_box.get()   # read the weight text box

    # ---- Step 2: Make sure nothing is empty ----
    if height_text == "" or weight_text == "":
        messagebox.showwarning("Oops", "Please fill in both boxes.")
        return   # stop here if empty

    # ---- Step 3: Try to turn the text into numbers ----
    try:
        height = float(height_text)
        weight = float(weight_text)
    except:
        # If the user typed letters instead of numbers
        messagebox.showerror("Oops", "Please type numbers only.")
        return

    # ---- Step 4: Make sure the numbers are positive ----
    if height <= 0 or weight <= 0:
        messagebox.showerror("Oops", "Numbers must be bigger than 0.")
        return

    # ---- Step 5: Do the math ----
    # BMI formula:  weight / (height in meters)²
    height_in_meters = height / 100       # 170 cm → 1.70 m
    bmi = weight / (height_in_meters * height_in_meters)

    # ---- Step 6: Decide the category ----
    if bmi < 18.5:
        category = "Underweight"
    elif bmi < 25:
        category = "Normal weight"
    elif bmi < 30:
        category = "Overweight"
    elif bmi < 35:
        category = "Obese Class I"
    elif bmi < 40:
        category = "Obese Class II"
    else:
        category = "Obese Class III"

    # ---- Step 7: Show the result on the screen ----
    # "%.2f" means: show 2 decimal places
    result_number.config(text="%.2f" % bmi)
    result_category.config(text=category)

    # ---- Step 8: Save this calculation to history ----
    line = ("Height: %.1f cm | Weight: %.1f kg | BMI: %.2f | %s"
            % (height, weight, bmi, category))
    history.append(line)


# ================================================================
# PART 3: THE MENU BUTTONS
# ================================================================

def show_history():
    """Show all past calculations in a popup."""
    # If the list is empty, tell the user
    if len(history) == 0:
        messagebox.showinfo("History", "You haven't calculated anything yet.")
        return

    # Build one big string with all the records
    all_text = ""
    number = 1
    for line in history:
        all_text = all_text + str(number) + ". " + line + "\n"
        number = number + 1

    # Show the string in a popup
    messagebox.showinfo("History", all_text)


def clear_history():
    """Delete all past calculations."""
    # If already empty, tell the user
    if len(history) == 0:
        messagebox.showinfo("History", "Nothing to clear.")
        return

    # Ask the user to confirm
    answer = messagebox.askyesno("Confirm", "Delete all history?")
    if answer == True:
        history.clear()          # empty the list
        messagebox.showinfo("History", "History cleared!")


def show_statistics():
    """Show how many calculations we've done."""
    if len(history) == 0:
        messagebox.showinfo("Statistics", "No calculations yet.")
        return

    messagebox.showinfo("Statistics",
                        "You have calculated " + str(len(history)) + " time(s).")


def show_about():
    """Show info about the program."""
    messagebox.showinfo("About",
                        "BMI Calculator\n"
                        "Version 1.0\n\n"
                        "Made with Python by Renz A. General")


def exit_app():
    """Close the window."""
    window.destroy()


# ================================================================
# PART 4: MAKE THE WINDOW
# ================================================================
window = tk.Tk()
window.title("BMI Calculator")
window.geometry("400x520")
window.config(bg="#f0f0f5")


# ================================================================
# PART 5: MAKE THE MENU BAR
# ================================================================
menu_bar = tk.Menu(window)

# File menu
file_menu = tk.Menu(menu_bar, tearoff=0)
file_menu.add_command(label="Clear History", command=clear_history)
file_menu.add_separator()
file_menu.add_command(label="Exit", command=exit_app)
menu_bar.add_cascade(label="File", menu=file_menu)

# View menu
view_menu = tk.Menu(menu_bar, tearoff=0)
view_menu.add_command(label="Show History", command=show_history)
view_menu.add_command(label="Show Statistics", command=show_statistics)
menu_bar.add_cascade(label="View", menu=view_menu)

# Help menu
help_menu = tk.Menu(menu_bar, tearoff=0)
help_menu.add_command(label="About", command=show_about)
menu_bar.add_cascade(label="Help", menu=help_menu)

window.config(menu=menu_bar)


# ================================================================
# PART 6: MAKE THE TITLE
# ================================================================
title = tk.Label(window, text="BMI Calculator",
                 font=("Arial", 22, "bold"),
                 bg="#f0f0f5", fg="#cc1155")
title.pack(pady=(20, 0))

subtitle = tk.Label(window, text="Based on WHO standards",
                    font=("Arial", 10),
                    bg="#f0f0f5", fg="gray")
subtitle.pack(pady=(0, 20))


# ================================================================
# PART 7: MAKE THE HEIGHT INPUT
# ================================================================
height_label = tk.Label(window, text="Height (in cm):",
                        font=("Arial", 12),
                        bg="#f0f0f5")
height_label.pack(anchor="w", padx=40)

height_box = tk.Entry(window, font=("Arial", 14))
height_box.pack(fill="x", padx=40, pady=(4, 14), ipady=5)


# ================================================================
# PART 8: MAKE THE WEIGHT INPUT
# ================================================================
weight_label = tk.Label(window, text="Weight (in kg):",
                        font=("Arial", 12),
                        bg="#f0f0f5")
weight_label.pack(anchor="w", padx=40)

weight_box = tk.Entry(window, font=("Arial", 14))
weight_box.pack(fill="x", padx=40, pady=(4, 20), ipady=5)


# ================================================================
# PART 9: MAKE THE CALCULATE BUTTON
# ================================================================
calc_button = tk.Button(window,
                        text="Calculate BMI",
                        font=("Arial", 14, "bold"),
                        bg="#cc1155", fg="white",
                        command=calculate_bmi)
calc_button.pack(fill="x", padx=40, ipady=8)


# ================================================================
# PART 10: MAKE THE RESULT AREA
# ================================================================
result_number = tk.Label(window, text="—",
                         font=("Arial", 30, "bold"),
                         bg="#f0f0f5", fg="#cc1155")
result_number.pack(pady=(20, 0))

result_category = tk.Label(window, text="Enter your details",
                           font=("Arial", 14),
                           bg="#f0f0f5", fg="gray")
result_category.pack()


# ================================================================
# PART 11: START THE PROGRAM
# ================================================================
window.mainloop()   # keep the window open