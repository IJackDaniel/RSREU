#define MI_OPEN 101
#define MI_EXIT 102
#define MI_SETTINGS 103
#define ID_DOTS 201
#define ID_CURVE 202
#define ID_SCALE 203
#define IDD_SETTINGS 300

#define IDOK 1
#define IDCANCEL 2

enum GraphModes { DOTS = ID_DOTS, CURVE = ID_CURVE };

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
void OnCommand(HWND, int, HWND, UINT);
void OnPaint(HWND);
void OnDestroy(HWND);

INT_PTR CALLBACK DlgProc(HWND, UINT, WPARAM, LPARAM);
BOOL DlgOnInitDialog(HWND, HWND, LPARAM);
void DlgOnCommand(HWND, int, HWND, UINT);
