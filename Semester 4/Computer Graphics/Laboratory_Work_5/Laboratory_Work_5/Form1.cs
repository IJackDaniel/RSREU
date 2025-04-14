using System;
using System.Drawing;
using System.Windows.Forms;
using System.Collections.Generic;
using static System.Net.Mime.MediaTypeNames;
using System.Security.Cryptography;

namespace Laboratory_Work_5
{
    public partial class Form1 : Form
    {
        int width = 1395;
        int height = 957;
        Random random = new Random();
        List<Point> grassClusters = new List<Point>();

        // Настройки травы
        Color grassBladeColor = Color.FromArgb(100, 180, 60);
        float bladeThickness = 3.5f;
        int minBladeHeight = 50;
        int maxBladeHeight = 80;

        public Form1()
        {
            InitializeComponent();
            //this.Paint += new PaintEventHandler(Form1_Paint);
            this.Paint += new PaintEventHandler(DrawFootballPlayer);
            this.ClientSize = new Size(width, height);
            this.FormBorderStyle = FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;

            GenerateUniformGrassClusters();
        }

        private void GenerateUniformGrassClusters()
        {
            int skyHeight = height * 2 / 5;
            int grassTop = skyHeight + 60;
            int grassBottom = height - 40;
            int grassHeight = grassBottom - grassTop;

            int cols = 3;
            int rows = 3;
            int colWidth = width / cols;
            int rowHeight = grassHeight / rows;

            List<Point> gridPoints = new List<Point>();
            for (int r = 0; r < rows; r++)
            {
                for (int c = 0; c < cols; c++)
                {
                    if (!(r == 1 && c == 1))
                    {
                        int x = c * colWidth + colWidth / 2;
                        int y = grassTop + r * rowHeight + rowHeight / 2;
                        x += random.Next(-colWidth / 3, colWidth / 3);
                        y += random.Next(-rowHeight / 3, rowHeight / 3);
                        x = Math.Clamp(x, 50, width - 50);
                        y = Math.Clamp(y, grassTop, grassBottom);
                        gridPoints.Add(new Point(x, y));
                    }
                }
            }
            grassClusters = gridPoints;
        }

        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            Graphics graphics = e.Graphics;
            int skyHeight = height * 2 / 5;
            int transitionHeight = 10;

            // Фон
            graphics.FillRectangle(Brushes.SkyBlue, 0, 0, width, skyHeight);
            graphics.FillRectangle(Brushes.LightGreen, 0, skyHeight - transitionHeight / 2, width, transitionHeight);
            graphics.FillRectangle(Brushes.Green, 0, skyHeight + transitionHeight / 2, width, height - skyHeight - transitionHeight / 2);

            DrawGrass(graphics);
            DrawFootballGoal(graphics, skyHeight, transitionHeight);
            DrawFootballBall(graphics);
        }
        
        private void DrawGrass(Graphics g)
        {
            using (Pen grassPen = new Pen(grassBladeColor, bladeThickness))
            {
                foreach (Point center in grassClusters)
                {
                    for (int i = 0; i < 40; i++)
                    {
                        int xStart = center.X + random.Next(-8, 8);
                        int yStart = center.Y + random.Next(-8, 8);
                        int xEnd = xStart + random.Next(-50, 50);
                        int yEnd = yStart - random.Next(minBladeHeight, maxBladeHeight);
                        g.DrawLine(grassPen, xStart, yStart, xEnd, yEnd);
                    }
                }
            }
        }

        private void DrawFootballGoal(Graphics g, int skyHeight, int transitionHeight)
        {
            // Параметры ворот
            int goalXLeft = width / 3;
            int goalXRight = width - width / 6;
            int goalWidth = goalXRight - goalXLeft;
            int goalTop = height / 17;
            int goalBottom = skyHeight - transitionHeight / 2;
            int goalHeight = goalBottom - goalTop;

            // Увеличенная толщина перекладин
            int goalPostThickness = 15; // Было 10
            int crossbarThickness = 15; // Новая переменная для горизонтальной перекладины

            // Цвета
            Color goalColor = Color.White;
            Color outlineColor = Color.Gray;
            Color netColor = Color.FromArgb(80, 80, 80);

            // 1. Вертикальные стойки (толщина 15px)
            g.FillRectangle(new SolidBrush(goalColor),
                goalXLeft, goalTop,
                goalPostThickness, goalHeight);
            g.DrawRectangle(new Pen(outlineColor, 2),
                goalXLeft, goalTop,
                goalPostThickness, goalHeight);

            g.FillRectangle(new SolidBrush(goalColor),
                goalXRight, goalTop,
                goalPostThickness, goalHeight);
            g.DrawRectangle(new Pen(outlineColor, 2),
                goalXRight, goalTop,
                goalPostThickness, goalHeight);

            // 2. Горизонтальная перекладина (толщина 15px)
            g.FillRectangle(new SolidBrush(goalColor),
                goalXLeft, goalTop,
                goalWidth + goalPostThickness, crossbarThickness);
            g.DrawRectangle(new Pen(outlineColor, 2),
                goalXLeft, goalTop,
                goalWidth + goalPostThickness, crossbarThickness);

            // 3. Сетка
            using (Pen netPen = new Pen(netColor, 2))
            {
                int netCellSize = 25;
                // Вертикальные линии
                for (int x = goalXLeft + goalPostThickness; x < goalXRight; x += netCellSize)
                {
                    g.DrawLine(netPen, x, goalTop + crossbarThickness, x, goalBottom);
                }
                // Горизонтальные линии
                for (int y = goalTop + crossbarThickness; y < goalBottom; y += netCellSize)
                {
                    g.DrawLine(netPen, goalXLeft + goalPostThickness, y, goalXRight, y);
                }
            }
        }
        private void DrawFootballBall(Graphics g)
        {
            // Позиция центра мяча
            int ballCenterX = width / 2 - width / 16; // 1/16 от центра влево
            int ballCenterY = height / 2 + height / 6; // 1/6 от центра вниз
            int ballRadius = 40; // Размер мяча

            // Рисуем мяч
            g.FillEllipse(Brushes.White,
                ballCenterX - ballRadius,
                ballCenterY - ballRadius,
                ballRadius * 2,
                ballRadius * 2);

            g.DrawEllipse(new Pen(Color.Black, 3),
                ballCenterX - ballRadius,
                ballCenterY - ballRadius,
                ballRadius * 2,
                ballRadius * 2);
        }

        private PictureBox pictureBox;
        private void DrawFootballPlayer(object sender, PaintEventArgs e)
        {
            pictureBox = new PictureBox
            {
                SizeMode = PictureBoxSizeMode.Zoom,
                Dock = DockStyle.Fill
            };
            this.Controls.Add(pictureBox);
            try
            {
                pictureBox.Image = new Bitmap("C:\\Programming\\RSREU\\Semester 4\\Computer Graphics\\Laboratory_Work_5\\sourse\\man.png");
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Ошибка: {ex.Message}");
            }
        }

    }
}

