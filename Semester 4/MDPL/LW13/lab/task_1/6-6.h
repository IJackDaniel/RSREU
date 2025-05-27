#define MI_OPEN 101
#define MI_EXIT 102

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
void OnCommand(HWND, int, HWND, UINT);
void OnPaint(HWND);
void OnDestroy(HWND);