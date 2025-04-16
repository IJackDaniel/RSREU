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
            this.Paint += new PaintEventHandler(Form1_Paint);
            //this.Paint += new PaintEventHandler(DrawFootballPlayer);
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
            DrawFootballPlayer(graphics);
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

        //private PictureBox pictureBox;
        //private void DrawFootballPlayer(object sender, PaintEventArgs e)
        //{
        //    pictureBox = new PictureBox
        //    {
        //        SizeMode = PictureBoxSizeMode.Zoom,
        //        Dock = DockStyle.Fill
        //    };
        //    this.Controls.Add(pictureBox);
        //    try
        //    {
        //        pictureBox.Image = new Bitmap("C:\\Programming\\RSREU\\Semester 4\\Computer Graphics\\Laboratory_Work_5\\sourse\\man.png");
        //    }
        //    catch (Exception ex)
        //    {
        //        MessageBox.Show($"Ошибка: {ex.Message}");
        //    }
        //}

        private void DrawFootballPlayer(Graphics g)
        {
            // Кисти и карандаши
            using (Pen blackPen = new Pen(Color.Black, 2))
            using (Brush hairBrush = new SolidBrush(Color.SaddleBrown))
            using (Brush bodyBrush = new SolidBrush(Color.Red))
            using (Brush pantsBrush = new SolidBrush(Color.Blue))
            using (Brush shoesBrush = new SolidBrush(Color.Black))
            using (Brush handsBrush = new SolidBrush(Color.White))
            using (Brush skinBrush = new SolidBrush(Color.PeachPuff))
            {


                // Туловище
                Point[] body = {
                    new Point(189, 557),
                    new Point(247, 525),
                    new Point(319, 512), // Начало шеи
                    new Point(344, 532),
                    new Point(361, 524), // Конец шеи
                    new Point(382, 531),
                    new Point(405, 591),
                    new Point(390, 608),
                    new Point(341, 601), // начало кисти
                    new Point(338, 587), // конец кисти
                    new Point(378, 590),
                    new Point(366, 574),

                    new Point(359, 588),
                    new Point(338, 587),
                    new Point(341, 601),
                    new Point(353, 604),

                    new Point(345, 623), // правые штаны
                    new Point(300, 631),
                    new Point(262, 614), // левые штаны
                    new Point(278, 602),
                    new Point(293, 547),
                    new Point(264, 552),
                    new Point(194, 574),
                };
                g.FillPolygon(bodyBrush, body);
                g.DrawPolygon(blackPen, body);

                // Кисть 1
                Point[] hand_1 = {
                    new Point(341, 601), // начало кисти
                    new Point(301, 596),
                    new Point(310, 586), 
                    new Point(338, 587), // конец кисти
                };
                g.FillPolygon(handsBrush, hand_1);
                g.DrawPolygon(blackPen, hand_1);

                // Кисть 2
                Point[] hand_2 = {
                    new Point(189, 557),
                    new Point(192, 565),
                    new Point(166, 600),
                    new Point(166, 587),
                    new Point(135, 592),
                    new Point(137, 573),
                    new Point(160, 573),
                };
                g.FillPolygon(handsBrush, hand_2);
                g.DrawPolygon(blackPen, hand_2);


                // левая нога
                Point[] left_leg = {
                    new Point(345, 623), // правые штаны
                    new Point(300, 631),
                    new Point(262, 614), // левые штаны
                    new Point(298, 704),
                    new Point(323, 712),
                    new Point(340, 748),
                    new Point(295, 800), //
                    new Point(331, 816),
                    new Point(374, 751),
                };
                g.FillPolygon(pantsBrush, left_leg);
                g.DrawPolygon(blackPen, left_leg);


                // правая нога
                Point[] right_leg = {
                    new Point(345, 623), // правые штаны
                    new Point(300, 631),
                    new Point(262, 614), // левые штаны
                    new Point(275, 655),
                    new Point(310, 689),
                    new Point(396, 665),
                    new Point(453, 707),
                    new Point(476, 694), // Начало ступни
                    new Point(410, 637),
                };
                g.FillPolygon(pantsBrush, right_leg);
                g.DrawPolygon(blackPen, right_leg);

                // левея ступня
                Point[] left_foot = {
                    new Point(295, 800),
                    new Point(276, 819),
                    new Point(338, 865),
                    new Point(361, 856),
                    new Point(349, 836),
                    new Point(331, 816)
                };
                g.FillPolygon(shoesBrush, left_foot);
                g.DrawPolygon(blackPen, left_foot);

                // правая ступня
                Point[] right_foot = {
                    new Point(476, 694),
                    new Point(437, 717),
                    new Point(498, 758),
                    new Point(495, 727),
                };
                g.FillPolygon(shoesBrush, right_foot);
                g.DrawPolygon(blackPen, right_foot);

                // Белая тыква
                Point[] head = {
                    new Point(333, 523),
                    new Point(351, 490),
                    new Point(367, 490),
                    new Point(390, 463),
                    new Point(406, 460),
                    new Point(425, 478),
                    new Point(396, 509),
                    new Point(384, 509),
                    new Point(360, 520),
                    new Point(361, 524),
                    new Point(344, 532),
                };
                g.FillPolygon(handsBrush, head);
                g.DrawPolygon(blackPen, head);

                // волосы
                Point[] hair = {
                    new Point(333, 523),
                    new Point(319, 512),
                    new Point(321, 451),
                    new Point(350, 428),
                    new Point(398, 428),
                    new Point(415, 442),
                    new Point(406, 460),
                    new Point(390, 463),
                    new Point(367, 490),
                    new Point(351, 490),
                };
                g.FillPolygon(hairBrush, hair);
                g.DrawPolygon(blackPen, hair);

                // глаз
                Point[] eye = {
                    new Point(404, 482),
                    new Point(394, 480),
                    new Point(404, 470),
                };
                g.FillPolygon(shoesBrush, eye);
                g.DrawPolygon(blackPen, eye);

            }
        }

    }
}

