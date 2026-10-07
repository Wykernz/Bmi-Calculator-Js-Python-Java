import tkinter as tk
from tkinter import messagebox

# ================================================================
# Create the window
# ================================================================
window = tk.Tk()
window.title("BMI Calculator")
window.geometry("300x300")
window.resizable(False, False)

history = []   # list of past calculations

# ================================================================
# MENU ACTIONS
# ================================================================
def clear_history():
    if len(history) == 0:
        messagebox.showinfo("Clear", "Nothing to clear.")
        return
    answer = messagebox.askyesno("Confirm", "Delete all history?")
    if answer:
        history.clear()
        messagebox.showinfo("Clear", "History cleared!")


def show_history():
    if len(history) == 0:
        messagebox.showinfo("History", "You haven't calculated anything yet.")
        return
    text = ""
    for i in range(len(history)):
        text += f"{i + 1}. {history[i]}\n"
    messagebox.showinfo("History", text)


def show_statistics():
    if len(history) == 0:
        messagebox.showinfo("Statistics", "No calculations yet.")
        return
    messagebox.showinfo("Statistics",
                        f"You have calculated {len(history)} time(s).")


def show_about():
    messagebox.showinfo("About",
                        "BMI Calculator\n"
                        "Version 1.0\n\n"
                        "Made with Python by Renz A. General")


# ================================================================
# MENU BAR — File, View, Help
# ================================================================
menu_bar = tk.Menu(window)

# ---- File menu ----
file_menu = tk.Menu(menu_bar, tearoff=0)
file_menu.add_command(label="Clear History", command=clear_history)
file_menu.add_separator()
file_menu.add_command(label="Exit", command=window.destroy)
menu_bar.add_cascade(label="File", menu=file_menu)

# ---- View menu ----
view_menu = tk.Menu(menu_bar, tearoff=0)
view_menu.add_command(label="Show History", command=show_history)
view_menu.add_command(label="Show Statistics", command=show_statistics)
menu_bar.add_cascade(label="View", menu=view_menu)

# ---- Help menu ----
help_menu = tk.Menu(menu_bar, tearoff=0)
help_menu.add_command(label="About", command=show_about)
menu_bar.add_cascade(label="Help", menu=help_menu)

# Attach the menu bar to the window
window.config(menu=menu_bar)

# ================================================================
# Create the components
# ================================================================
height_label = tk.Label(window, text="Height (cm):")
height_box = tk.Entry(window, width=10)

weight_label = tk.Label(window, text="Weight (kg):")
weight_box = tk.Entry(window, width=10)

button = tk.Button(window, text="Calculate",
                   bg="#CC1155", fg="white",
                   activebackground="#A00E44", activeforeground="white",
                   font=("Arial", 10, "bold"),
                   padx=10, pady=4,
                   borderwidth=0)

result = tk.Label(window, text="Result will appear here",
                  font=("Arial", 11, "bold"),
                  fg="#CC1155")


# ================================================================
# What happens when the button is clicked
# ================================================================
def on_click():
    try:
        height = float(height_box.get())
        weight = float(weight_box.get())
    except ValueError:
        result.config(text="Please enter numbers only!", fg="red")
        return

    meters = height / 100
    bmi = weight / (meters * meters)

    if bmi < 18.5:
        category = "Underweight"
        color = "#3498DB"
    elif bmi < 25:
        category = "Normal weight"
        color = "#27AE60"
    elif bmi < 30:
        category = "Overweight"
        color = "#F39C12"
    else:
        category = "Obese"
        color = "#E74C3C"

    result.config(text=f"BMI: {bmi:.2f} ({category})", fg=color)

    # Save to history
    line = (f"Height: {height:.1f} cm | Weight: {weight:.1f} kg "
            f"| BMI: {bmi:.2f} | {category}")
    history.append(line)


button.config(command=on_click)

# ================================================================
# Add components to the window
# ================================================================
height_label.pack(pady=(30, 0))
height_box.pack()
weight_label.pack(pady=(10, 0))
weight_box.pack()
button.pack(pady=15)
result.pack(pady=5)

# ================================================================
# Show the window
# ================================================================
window.mainloop()