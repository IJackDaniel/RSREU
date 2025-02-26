import tkinter as tk
import tkinter.ttk as ttk
from tkinter import messagebox as mb
import os
import tempfile


def close_window():
    """ Выход из приложения """
    if mb.askyesno("Выход", "Завершить работу?"):
        window.destroy()


def program_description():
    """ Отображение окна О программе """
    mb.showinfo("О программе", "Программа позволяет вам рассчитать стоимость партий товаров в магазине CorgiShop")


def about_show():
    """ Окно с информацией об авторе"""
    mb.showinfo("Об авторе", "Чтобы поддержать автора, вы можете перевести деньги по номеру\
 +7-900-602-43-10 на Сбербанк или Тинькофф. Он очень хороший человек и любит корги")


def read_file_history():  #
    """ Отображение истории рассчётов цен """
    if not os.path.exists("data.txt"):
        mb.showinfo(title="Информация", message="Нет истории рассчётов")
    else:
        text.delete(1.0, tk.END)
        with open("data.txt", 'r') as f:
            text.insert(1.0, f.read())
        text.focus_set()


def save_file_history():
    """ Запись результата в файл с историей рассчётов """
    if not os.path.exists("data.txt"):
        f = open("data.txt", "w")
        f.close()
    with open("data.txt", 'a') as f:
        letter = label_text['text'] + '\n ' + label_root['text'] + '\n'
        f.writelines(letter)


def radiobutton_change():
    """ Различные параметры в зависимости от выбора вида товара"""
    if rb_var.get() == 0 or rb_var.get() == 1 or rb_var.get() == 2:
        """ Скрытие элементов """
        entry_hat.grid_remove()
        label_hat.grid_remove()
        """ Отображение элементов """
        entry_xs.grid()
        entry_s.grid()
        entry_m.grid()
        entry_l.grid()
        entry_xl.grid()
        entry_xxl.grid()
        entry_xxxl.grid()
        label_xs.grid()
        label_s.grid()
        label_m.grid()
        label_l.grid()
        label_xl.grid()
        label_xxl.grid()
        label_xxxl.grid()
    elif rb_var.get() == 3 or rb_var.get() == 4:
        """ Скрытие элементов """
        entry_xs.grid_remove()
        label_xs.grid_remove()
        entry_s.grid_remove()
        label_s.grid_remove()
        entry_m.grid_remove()
        label_m.grid_remove()
        entry_l.grid_remove()
        label_l.grid_remove()
        entry_xl.grid_remove()
        label_xl.grid_remove()
        entry_xxl.grid_remove()
        label_xxl.grid_remove()
        entry_xxxl.grid_remove()
        label_xxxl.grid_remove()
        """ Отображение элементов """
        entry_hat.grid()
        label_hat.grid()
    data_clear()


def popup(event):
    """ Определение выбора пункта контекстного меню """
    popupmenu.post(event.x_root, event.y_root)


def history_clear():
    """ Очистка многострочного текстового поля """
    answer = mb.askyesno(title="Подтверждение действия", message="Очистить историю рассчётов?")
    if answer:
        text.delete(1.0, tk.END)
        f = open("data.txt", "w")
        f.close()


def data_clear():
    """ Очистка полей для ввода и поля с ценой """
    entry_xs.delete(0, tk.END)
    entry_s.delete(0, tk.END)
    entry_m.delete(0, tk.END)
    entry_l.delete(0, tk.END)
    entry_xl.delete(0, tk.END)
    entry_xxl.delete(0, tk.END)
    entry_xxxl.delete(0, tk.END)
    entry_hat.delete(0, tk.END)
    label_root['text'] = ''
    label_text['text'] = ''
    button_save['state'] = 'disable'
    button_clear['state'] = 'disable'


def run_solution():
    """ Считывание параметров """
    global xs, s, m, l, xl, xxl, xxxl, hat
    name = ""
    try:
        if rb_var.get() == 0 or rb_var.get() == 1 or rb_var.get() == 2:
            xs = int(entry_xs.get()) if entry_xs.get() != "" else 0
            s = int(entry_s.get()) if entry_s.get() != "" else 0
            m = int(entry_m.get()) if entry_m.get() != "" else 0
            l = int(entry_l.get()) if entry_l.get() != "" else 0
            xl = int(entry_xl.get()) if entry_xl.get() != "" else 0
            xxl = int(entry_xxl.get()) if entry_xxl.get() != "" else 0
            xxxl = int(entry_xxxl.get()) if entry_xxxl.get() != "" else 0

        if rb_var.get() == 3 or rb_var.get() == 4:
            hat = int(entry_hat.get()) if entry_hat.get() != "" else 0
    except ValueError:
        mb.showerror('Ошибка!', 'Проверьте ввод!')
    except Exception:
        mb.showerror('Ошибка!', 'Непонятная ошибка.')
    else:
        if rb_var.get() == 0 or rb_var.get() == 1 or rb_var.get() == 2:
            cnt = xs + s + m + l + xl + xxl + xxxl
            type_prod = rb_var.get()
            cost(cnt, type_prod)
            match type_prod:
                case 0:
                    name = "футболок"
                case 1:
                    name = "свитшов"
                case 2:
                    name = "худи"
            label_text['text'] = f"Цена для {cnt} {name}"
        else:
            cnt = hat
            type_prod = rb_var.get()
            cost(cnt, type_prod)
            match type_prod:
                case 3:
                    name = "панамок"
                case 4:
                    name = "кепок"
            label_text['text'] = f"Цена для {cnt} {name}"
        button_save['state'] = 'active'
        button_clear['state'] = 'active'


def cost(cnt, type_pr):
    """ Поиск корней линейного и квадратного уравнения """
    cst = 0
    match type_pr:
        case 0:  # футболка
            if cnt >= 500:
                cst = 600
            elif cnt >= 300:
                cst = 800
            elif cnt >= 150:
                cst = 1000
            elif cnt >= 50:
                cst = 1100
            else:
                cst = 1300
            label_root['text'] = f"{cst*cnt} рублей"
        case 1:  # свитшот
            if cnt >= 500:
                cst = 1600
            elif cnt >= 300:
                cst = 1800
            elif cnt >= 150:
                cst = 2100
            elif cnt >= 50:
                cst = 2300
            else:
                cst = 2500
            label_root['text'] = f"{cst*cnt} рублей"
        case 2:  # худи
            if cnt >= 500:
                cst = 1600
            elif cnt >= 300:
                cst = 1800
            elif cnt >= 150:
                cst = 2100
            elif cnt >= 50:
                cst = 2300
            else:
                cst = 2500
            label_root['text'] = f"{cst*cnt} рублей"
        case 3:  # панамка
            if cnt >= 500:
                cst = 250
            elif cnt >= 300:
                cst = 280
            elif cnt >= 150:
                cst = 320
            elif cnt >= 50:
                cst = 370
            else:
                cst = 400
            label_root['text'] = f"{cst*cnt} рублей"
        case 4:  # кепка
            if cnt >= 500:
                cst = 250
            elif cnt >= 300:
                cst = 280
            elif cnt >= 150:
                cst = 320
            elif cnt >= 50:
                cst = 370
            else:
                cst = 400
            label_root['text'] = f"{cst * cnt} рублей"


# Настройка окна
window = tk.Tk()
window.title("Рассчёт цен на CorgiShop")
window.iconbitmap(default=r"Images\icon.ico")
window.geometry('320x450')
window.resizable(False, False)

# Настройка главного меню
mainmenu = tk.Menu(window)
window.config(menu=mainmenu)

# Создание выпадающих списков команд
filemenu = tk.Menu(mainmenu, tearoff=0)
filemenu.add_command(label="Открыть...", command=read_file_history)
filemenu.add_separator()
filemenu.add_command(label="Выход", command=close_window)
helpmenu = tk.Menu(mainmenu, tearoff=0)
helpmenu.add_command(label="О программе", command=program_description)
helpmenu.add_command(label="Об авторе", command=about_show)

# Подвязыванием экземпляры меню к главному меню
mainmenu.add_cascade(label="Файл", menu=filemenu)
mainmenu.add_cascade(label="Справка", menu=helpmenu)

# Создание вкладок
nb = ttk.Notebook(window)
nb.pack(fill='both', expand=True)
frame1 = tk.Frame(window)
frame2 = tk.Frame(window)
nb.add(frame1, text="Рассчёт цены")
nb.add(frame2, text="Просмотр истории рассчётов")

# Размещение виджетов на вкладке "Рассчёт цены"
frame_koef = tk.LabelFrame(frame1, text="Выберите товар")
# frame_koef.grid(row=0, column=0, columnspan=2, padx=15, pady=5)
frame_koef.place(x=15, y=10)

frame_type = tk.LabelFrame(frame1, text="Тип одежды")
# frame_type.grid(row=0, column=2, padx=5, pady=5)
frame_type.place(x=190, y=10)

frame_result = tk.Frame(frame1)
# frame_result.grid(row=1, column=0, columnspan=2, padx=5, pady=5)
frame_result.place(x=15, y=320)

frame_button = tk.Frame(frame1)
# frame_button.grid(row=1, column=2, padx=5, pady=5)
frame_button.place(x=185, y=320)

label_xs = tk.Label(frame_koef, text="XS", font=("Times New Roman", 11), fg="blue")
label_s = tk.Label(frame_koef, text="S", font=("Times New Roman", 11), fg="blue")
label_m = tk.Label(frame_koef, text="M", font=("Times New Roman", 11), fg="blue")
label_l = tk.Label(frame_koef, text="L", font=("Times New Roman", 11), fg="blue")
label_xl = tk.Label(frame_koef, text="XL", font=("Times New Roman", 11), fg="blue")
label_xxl = tk.Label(frame_koef, text="XXL", font=("Times New Roman", 11), fg="blue")
label_xxxl = tk.Label(frame_koef, text="XXXL", font=("Times New Roman", 11), fg="blue")
label_xs.grid(row=0, column=0, padx=5, pady=5)
label_s.grid(row=1, column=0, padx=5, pady=5)
label_m.grid(row=2, column=0, padx=5, pady=5)
label_l.grid(row=3, column=0, padx=5, pady=5)
label_xl.grid(row=4, column=0, padx=5, pady=5)
label_xxl.grid(row=5, column=0, padx=5, pady=5)
label_xxxl.grid(row=6, column=0, padx=5, pady=5)

label_hat = tk.Label(frame_koef, text="Count", font=("Times New Roman", 11), fg="blue")
label_hat.grid(row=7, column=0, padx=5, pady=5)
label_hat.grid_remove()

entry_xs = tk.Entry(frame_koef, width=10)
entry_xs.grid(row=0, column=1, padx=5, pady=10)
entry_s = tk.Entry(frame_koef, width=10)
entry_s.grid(row=1, column=1, padx=5, pady=10)
entry_m = tk.Entry(frame_koef, width=10)
entry_m.grid(row=2, column=1, padx=5, pady=10)
entry_l = tk.Entry(frame_koef, width=10)
entry_l.grid(row=3, column=1, padx=5, pady=10)
entry_xl = tk.Entry(frame_koef, width=10)
entry_xl.grid(row=4, column=1, padx=5, pady=10)
entry_xxl = tk.Entry(frame_koef, width=10)
entry_xxl.grid(row=5, column=1, padx=5, pady=10)
entry_xxxl = tk.Entry(frame_koef, width=10)
entry_xxxl.grid(row=6, column=1, padx=5, pady=10)

entry_hat = tk.Entry(frame_koef, width=10)
entry_hat.grid(row=7, column=1, padx=5, pady=10)
entry_hat.grid_remove()

rb_var = tk.IntVar()
rb_var.set(0)

rb_tshirt = tk.Radiobutton(frame_type, variable=rb_var, text="Футболка", value=0, command=radiobutton_change)
rb_tshirt.grid(row=0, column=0, padx=5, pady=5)
rb_sw = tk.Radiobutton(frame_type, variable=rb_var, text="Свитшот", value=1, command=radiobutton_change)
rb_sw.grid(row=1, column=0, padx=5, pady=5)
rb_hud = tk.Radiobutton(frame_type, variable=rb_var, text="Худи", value=2, command=radiobutton_change, anchor="w")
rb_hud.grid(row=2, column=0, padx=5, pady=5)
rb_pan = tk.Radiobutton(frame_type, variable=rb_var, text="Панамка", value=3, command=radiobutton_change, anchor="w")
rb_pan.grid(row=3, column=0, padx=5, pady=5)
rb_cap = tk.Radiobutton(frame_type, variable=rb_var, text="Кепка", value=4, command=radiobutton_change, anchor="w")
rb_cap.grid(row=4, column=0, padx=5, pady=5)

label_text = tk.Label(frame_result)
label_text.grid(row=0, column=0)
label_root = tk.Label(frame_result)
label_root.grid(row=1, column=0)

button_res = tk.Button(frame_button, width=12, text="Узнать цену", command=run_solution)
button_res.grid(row=0, column=0, padx=5, pady=2)
button_clear = tk.Button(frame_button, state='disabled', width=12, text="Очистить", command=data_clear)
button_clear.grid(row=1, column=0, padx=5, pady=2)
button_save = tk.Button(frame_button, state='disabled', width=12, text="Сохранить", command=save_file_history)
button_save.grid(row=2, column=0, padx=5, pady=2)

# Размещение виджетов на вкладке "Просмотр истории"
# Многострочное текстовое поле с полосой прокрутки
text = tk.Text(frame2, wrap=tk.WORD)
scroll = tk.Scrollbar(frame2, command=text.yview)
scroll.pack(side=tk.RIGHT, fill=tk.Y)
text.config(yscrollcommand=scroll.set)
text.pack(side=tk.LEFT)

# Контекстное меню
popupmenu = tk.Menu(tearoff=0)
popupmenu.add_command(label="Очистить", command=history_clear)
popupmenu.add_command(label="Показать историю", command=read_file_history)
text.bind('<Button-3>', popup)
window.mainloop()

