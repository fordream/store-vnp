namespace KimCuong
{
    partial class FormMain
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.tg_cd = new System.Windows.Forms.Timer(this.components);
            this.tg_anhinh = new System.Windows.Forms.Timer(this.components);
            this.tg_kt = new System.Windows.Forms.Timer(this.components);
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.tg_progress = new System.Windows.Forms.Timer(this.components);
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.timer_picturebox = new System.Windows.Forms.Timer(this.components);
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.bt_HighScore = new System.Windows.Forms.Button();
            this.bt_Hint = new System.Windows.Forms.Button();
            this.bt_LoadGame = new System.Windows.Forms.Button();
            this.bt_SaveGame = new System.Windows.Forms.Button();
            this.btNewGame = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // tg_cd
            // 
            this.tg_cd.Interval = 24;
            this.tg_cd.Tick += new System.EventHandler(this.Chuyendong);
            // 
            // tg_anhinh
            // 
            this.tg_anhinh.Interval = 130;
            this.tg_anhinh.Tick += new System.EventHandler(this.AnHinh);
            // 
            // tg_kt
            // 
            this.tg_kt.Enabled = true;
            this.tg_kt.Interval = 20;
            this.tg_kt.Tick += new System.EventHandler(this.tg_kt_Tick);
            // 
            // progressBar1
            // 
            this.progressBar1.Location = new System.Drawing.Point(10, 693);
            this.progressBar1.Maximum = 180;
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(680, 25);
            this.progressBar1.Step = 1;
            this.progressBar1.TabIndex = 4;
            this.progressBar1.Value = 180;
            // 
            // tg_progress
            // 
            this.tg_progress.Interval = 1000;
            this.tg_progress.Tick += new System.EventHandler(this.tg_progress_Tick);
            // 
            // textBox1
            // 
            this.textBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 26.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBox1.ForeColor = System.Drawing.Color.Lime;
            this.textBox1.Location = new System.Drawing.Point(6, 63);
            this.textBox1.Multiline = true;
            this.textBox1.Name = "textBox1";
            this.textBox1.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.textBox1.Size = new System.Drawing.Size(143, 35);
            this.textBox1.TabIndex = 5;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.textBox1);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(748, 550);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(153, 148);
            this.groupBox1.TabIndex = 6;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Option";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(70, 18);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(0, 29);
            this.label3.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(14, 30);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(33, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Level";
            // 
            // timer1
            // 
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick_1);
            // 
            // timer_picturebox
            // 
            this.timer_picturebox.Interval = 50;
            this.timer_picturebox.Tick += new System.EventHandler(this.timer_picturebox_Tick);
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = global::WindowsFormsApplication1.Properties.Resources.UIT;
            this.pictureBox1.Location = new System.Drawing.Point(782, 433);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(79, 99);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.AutoSize;
            this.pictureBox1.TabIndex = 13;
            this.pictureBox1.TabStop = false;
            // 
            // bt_HighScore
            // 
            this.bt_HighScore.Image = global::WindowsFormsApplication1.Properties.Resources.High_Score;
            this.bt_HighScore.Location = new System.Drawing.Point(748, 350);
            this.bt_HighScore.Name = "bt_HighScore";
            this.bt_HighScore.Size = new System.Drawing.Size(143, 39);
            this.bt_HighScore.TabIndex = 8;
            this.bt_HighScore.UseVisualStyleBackColor = true;
            this.bt_HighScore.Click += new System.EventHandler(this.bt_HighScore_Click);
            // 
            // bt_Hint
            // 
            this.bt_Hint.Image = global::WindowsFormsApplication1.Properties.Resources.Hint1;
            this.bt_Hint.Location = new System.Drawing.Point(748, 140);
            this.bt_Hint.Name = "bt_Hint";
            this.bt_Hint.Size = new System.Drawing.Size(143, 39);
            this.bt_Hint.TabIndex = 3;
            this.bt_Hint.UseVisualStyleBackColor = true;
            this.bt_Hint.Click += new System.EventHandler(this.bt_Hint_Click);
            // 
            // bt_LoadGame
            // 
            this.bt_LoadGame.Image = global::WindowsFormsApplication1.Properties.Resources.Load_Game;
            this.bt_LoadGame.Location = new System.Drawing.Point(748, 280);
            this.bt_LoadGame.Name = "bt_LoadGame";
            this.bt_LoadGame.Size = new System.Drawing.Size(143, 39);
            this.bt_LoadGame.TabIndex = 2;
            this.bt_LoadGame.UseVisualStyleBackColor = true;
            this.bt_LoadGame.Click += new System.EventHandler(this.LoadGame);
            // 
            // bt_SaveGame
            // 
            this.bt_SaveGame.Image = global::WindowsFormsApplication1.Properties.Resources.Save_Game;
            this.bt_SaveGame.Location = new System.Drawing.Point(748, 210);
            this.bt_SaveGame.Name = "bt_SaveGame";
            this.bt_SaveGame.Size = new System.Drawing.Size(139, 42);
            this.bt_SaveGame.TabIndex = 1;
            this.bt_SaveGame.UseVisualStyleBackColor = true;
            this.bt_SaveGame.Click += new System.EventHandler(this.SaveGame);
            // 
            // btNewGame
            // 
            this.btNewGame.Image = global::WindowsFormsApplication1.Properties.Resources.New_Game;
            this.btNewGame.Location = new System.Drawing.Point(748, 70);
            this.btNewGame.Name = "btNewGame";
            this.btNewGame.Size = new System.Drawing.Size(143, 39);
            this.btNewGame.TabIndex = 0;
            this.btNewGame.UseVisualStyleBackColor = true;
            this.btNewGame.Click += new System.EventHandler(this.NewGame);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Font = new System.Drawing.Font("Times New Roman", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.Black;
            this.label2.Location = new System.Drawing.Point(778, 25);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(61, 24);
            this.label2.TabIndex = 14;
            this.label2.Text = "Score";
            this.label2.Visible = false;
            // 
            // FormMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(934, 722);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.bt_HighScore);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.progressBar1);
            this.Controls.Add(this.bt_Hint);
            this.Controls.Add(this.bt_LoadGame);
            this.Controls.Add(this.bt_SaveGame);
            this.Controls.Add(this.btNewGame);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.Name = "FormMain";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Kim Cuong";
            this.Load += new System.EventHandler(this.FormMain_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btNewGame;
        private System.Windows.Forms.Timer tg_cd;
        private System.Windows.Forms.Timer tg_anhinh;
        private System.Windows.Forms.Timer tg_kt;
        private System.Windows.Forms.Button bt_SaveGame;
        private System.Windows.Forms.Button bt_LoadGame;
        private System.Windows.Forms.Button bt_Hint;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.Timer tg_progress;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button bt_HighScore;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Timer timer_picturebox;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Label label2;
    }
}

