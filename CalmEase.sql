SHOW DATABASES;

use calmease;

SHOW TABLES;



CREATE TABLE `users` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `full_name` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(20),
    `user_type` ENUM('expert', 'user') NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



CREATE TABLE `session` (
    `session_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `session_date` DATE NOT NULL,
    `session_time` TIME NOT NULL,
    `duration` INT,
    `actual_start_time` DATETIME,
    `status` ENUM('scheduled', 'started', 'completed') DEFAULT 'scheduled',
    `agora_channel_id` VARCHAR(255) NOT NULL UNIQUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `expert_id` VARCHAR(255) NOT NULL,
    `expert_email` VARCHAR(255) NOT NULL
);


CREATE TABLE session_token (
    token_id INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
	user_id INT NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES session(session_id)
);


CREATE TABLE contactus (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);





CREATE TABLE articles (
    article_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    tags VARCHAR(255)
);




CREATE TABLE `breathing` (
    `breathing_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `duration` VARCHAR(50),
    `image` VARCHAR(255),
    `text_content` VARCHAR(255),
    `media_type` ENUM('video', 'audio', 'text') NOT NULL,
    `content_url` VARCHAR(255),
    `user_id` INT,
    `user_name` VARCHAR(255),
    `user_email` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) -- Assuming a `users` table exists with `user_id` as the primary key
);

CREATE TABLE `good_memories` (
    `memory_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `memory_date_time` DATETIME NOT NULL,
    `description` TEXT,
    `image_url` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `user_id` INT, -- References user who created the memory
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) -- Assuming a `users` table exists with `user_id` as the primary key
);


CREATE TABLE `meditation` (
    `meditation_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `category` VARCHAR(100), -- Stores the category of the meditation, e.g., "Mindfulness"
    `description` TEXT, -- Brief description of the meditation content
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `duration` VARCHAR(50), -- Duration of the meditation
    `media_type` ENUM('video', 'audio') NOT NULL, -- Indicates if it is video or audio content
    `content_url` VARCHAR(255), -- URL of the video/audio content
    `user_id` INT, -- References user who created or manages the meditation
    `user_name` VARCHAR(255),
    `user_email` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) -- Assuming a `users` table exists with `user_id` as the primary key
);


Select * from users;
select * from session;
select * from breathing;
select * from contactus;
select * from meditation;
select * from session_token;
select * from articles;

Select * from good_memories;


INSERT INTO `calmease`.`good_memories`
( `title`, `memory_date_time`, `description`, `image_url`, `created_at`, `updated_at`, `user_id`)
VALUES
('Sunset at the Beach', '2024-08-15 18:30:00', 'The warm hues of the sunset filled the sky as the waves gently kissed the shore.', 'https://example.com/images/sunset_beach.jpeg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 16),
( 'Family Picnic', '2024-07-20 12:00:00', 'A perfect afternoon with laughter, delicious food, and family bonding under the clear blue sky.', 'https://example.com/images/family_picnic.jpeg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 16),
( 'Graduation Day', '2024-05-10 14:00:00', 'The proudest moment of achieving a milestone with friends and family cheering.', 'https://example.com/images/graduation_day.jpeg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 16),
( 'First Snowfall', '2023-12-01 09:00:00', 'The magic of the first snowfall, creating a blanket of white and pure joy.', 'https://example.com/images/first_snowfall.jpeg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 16),
( 'Birthday Celebration', '2024-06-25 19:00:00', 'An unforgettable night filled with love, friends, cake, and candles.', 'https://example.com/images/birthday_celebration.jpeg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 16);


INSERT INTO `articles` ( `title`, `content`, `created_at`, `updated_at`, `user_id`, `user_name`, `user_email`, `image`, `tags`) VALUES
('The Art of Mindful Breathing', '<p>Mindful breathing is the cornerstone of any meditation practice. By focusing on the breath, we bring our attention to the present moment, helping to reduce stress and anxiety. This simple yet powerful technique allows us to reconnect with our inner self and find peace in the midst of chaos. Whether you are a beginner or an experienced meditator, mindful breathing is a practice that can be done anywhere, at any time.</p><p>Start by finding a quiet place to sit comfortably. Close your eyes and take a deep breath in through your nose, allowing your abdomen to rise. Exhale slowly through your mouth, letting go of any tension in your body. Continue to breathe deeply and slowly, focusing your attention on the sensation of the breath as it enters and leaves your body. If your mind starts to wander, gently bring your focus back to the breath.</p>', '2024-07-27 23:21:04', '2024-07-31 15:58:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1553524558-30b99f2f2f94', 'meditation, breathing, mindfulness'),
('Guided Meditation for Deep Relaxation', '<p>Guided meditation is a powerful tool for achieving deep relaxation and inner peace. In this article, we explore a step-by-step guided meditation that will help you release tension and stress from your body and mind. This practice is ideal for anyone looking to unwind after a long day or to prepare for a restful night\'s sleep.</p><p>Begin by sitting or lying down in a comfortable position. Close your eyes and take a few deep breaths, allowing your body to relax with each exhale. Imagine a warm, golden light surrounding your body, filling you with a sense of calm and tranquility. As you continue to breathe deeply, visualize the light spreading throughout your body, releasing any tension or stress you may be holding onto.</p>', '2024-07-27 23:22:04', '2024-07-31 15:59:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1553531384-cc64dd49f78d', 'meditation, relaxation, guided meditation'),
('The Benefits of Daily Meditation Practice', '<p>Daily meditation practice can have a profound impact on your overall well-being. From reducing stress and anxiety to improving concentration and emotional resilience, the benefits of meditation are numerous and well-documented. In this article, we explore the key benefits of making meditation a part of your daily routine and provide tips on how to get started.</p><p>One of the most significant benefits of daily meditation is its ability to reduce stress. By taking a few moments each day to focus on your breath and quiet your mind, you can create a sense of calm that carries over into the rest of your day. Additionally, meditation has been shown to improve emotional regulation, helping you to respond to challenges with greater patience and clarity.</p>', '2024-07-28 08:30:04', '2024-07-31 16:00:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1506214377099-90c9c5f43c35', 'meditation, daily practice, benefits'),
('Breathing Techniques for Stress Relief', '<p>Breathing techniques are a simple yet effective way to manage stress and promote relaxation. In this article, we will explore several breathing exercises that you can incorporate into your daily routine to help you stay calm and focused.</p><p>One of the most popular breathing techniques for stress relief is deep diaphragmatic breathing. This technique involves taking slow, deep breaths from the diaphragm, which helps to activate the body\'s relaxation response. To practice diaphragmatic breathing, sit or lie down in a comfortable position and place one hand on your abdomen. As you inhale, allow your abdomen to rise, and as you exhale, let it fall. Repeat this process for a few minutes, focusing on the sensation of the breath.</p>', '2024-07-28 10:45:04', '2024-07-31 16:01:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330', 'breathing, stress relief, techniques'),
('Meditation for Beginners: A Simple Guide', '<p>Meditation can seem daunting for beginners, but with the right guidance, it can become a simple and rewarding practice. In this article, we provide a step-by-step guide to help you get started with meditation, including tips on finding a comfortable position, focusing your mind, and creating a consistent practice.</p><p>Start by finding a quiet place where you won\'t be disturbed. Sit or lie down in a comfortable position and close your eyes. Begin by taking a few deep breaths, allowing your body to relax with each exhale. As you continue to breathe deeply, focus your attention on your breath, noticing the sensation as it enters and leaves your body. If your mind starts to wander, gently bring your focus back to the breath.</p>', '2024-07-28 12:00:04', '2024-07-31 16:02:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1528222354212-a295f02f92ff', 'meditation, beginners, guide'),
('Cultivating Calmness Through Meditation', '<p>Calmness is a state of mind that can be cultivated through regular meditation practice. In this article, we explore how meditation can help you develop a calm and peaceful mindset, even in the midst of life\'s challenges. We will also provide practical tips on how to incorporate meditation into your daily routine to achieve a greater sense of calm.</p><p>One of the key benefits of meditation is its ability to quiet the mind and reduce the impact of stress. By focusing on the present moment and letting go of worries about the past or future, you can create a sense of calm that permeates your entire being. Regular meditation practice can also help you develop greater emotional resilience, allowing you to navigate life\'s challenges with greater ease and grace.</p>', '2024-07-28 14:15:04', '2024-07-31 16:03:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1475232293689-accb7ee45b76', 'meditation, calmness, mindfulness'),
('The Science Behind Meditation', '<p>While meditation has been practiced for thousands of years, it is only in recent decades that science has begun to uncover its many benefits. In this article, we explore the scientific research behind meditation and how it affects the brain and body. From reducing stress and anxiety to improving focus and emotional regulation, the science behind meditation is both fascinating and compelling.</p><p>Research has shown that meditation can lead to changes in the brain\'s structure and function. For example, regular meditation practice has been found to increase the thickness of the prefrontal cortex, a part of the brain associated with attention and decision-making. Additionally, meditation has been shown to reduce the activity of the amygdala, a region of the brain involved in the stress response.</p>', '2024-07-28 16:30:04', '2024-07-31 16:04:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1513553404607-988bf2703777', 'meditation, science, research'),
('Finding Inner Peace Through Meditation', '<p>In a world filled with constant noise and distractions, finding inner peace can seem like an impossible task. However, meditation offers a pathway to inner tranquility that is accessible to everyone. In this article, we explore how meditation can help you find inner peace and provide tips on how to make meditation a regular part of your life.</p><p>Inner peace is not something that can be achieved overnight, but with regular meditation practice, it is possible to cultivate a deep sense of calm and contentment. By taking time each day to sit in stillness and focus on your breath, you can create a space of peace within yourself that is unaffected by external circumstances. This inner peace can then be carried with you throughout your day, helping you to navigate life\'s challenges with greater ease and grace.</p>', '2024-07-28 18:45:04', '2024-07-31 16:05:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1466626432011-021699b9442b', 'meditation, inner peace, tranquility'),
('The Role of Breathing in Meditation', '<p>Breathing plays a crucial role in meditation, serving as both an anchor for the mind and a tool for relaxation. In this article, we explore the importance of breathing in meditation and provide tips on how to use your breath to deepen your practice.</p><p>One of the key benefits of focusing on the breath during meditation is its ability to bring the mind into the present moment. By paying attention to the sensation of the breath as it enters and leaves the body, you can create a sense of calm and clarity that helps to quiet the mind. Additionally, deep, diaphragmatic breathing can activate the body\'s relaxation response, reducing stress and promoting a sense of well-being.</p>', '2024-07-28 21:00:04', '2024-07-31 16:06:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1470324161839-ce962d47cf1b', 'breathing, meditation, relaxation'),
('Meditation and Emotional Resilience', '<p>Meditation is not only a tool for relaxation but also a powerful practice for building emotional resilience. In this article, we explore how meditation can help you develop the inner strength to navigate life\'s challenges with grace and equanimity.</p><p>Emotional resilience is the ability to bounce back from adversity and maintain a sense of balance and well-being. Through regular meditation practice, you can cultivate the qualities of patience, compassion, and self-awareness that are essential for emotional resilience. By taking time each day to sit in stillness and focus on your breath, you can create a foundation of inner strength that supports you in facing life\'s challenges with greater ease and confidence.</p>', '2024-07-28 23:15:04', '2024-07-31 16:07:02', '1', 'Nikul Kukadiya', 'nikul@calmease.com', 'https://images.unsplash.com/photo-1501700493788-fa1a3e17d241', 'meditation, resilience, emotions');


INSERT INTO `calmease`.`session`
(`title`,
 `description`,
 `session_date`,
 `session_time`,
 `duration`,
 `actual_start_time`,
 `status`,
 `agora_channel_id`,
 `created_at`,
 `updated_at`,
 `expert_id`,
 `expert_email`)
VALUES
('Morning Mindfulness Meditation', 
 'Start your day with a refreshing mindfulness session.', 
 '2024-08-10', 
 '06:30:00', 
 '30', 
 '2024-08-10 06:30:00', 
 'scheduled', 
 '17', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '16', 
 'nikul@calmease.com'),
('Evening Calm Meditation', 
 'End your day with a calming meditation to release stress.', 
 '2024-08-10', 
 '20:00:00', 
 '40', 
 '2024-08-10 20:00:00', 
 'scheduled', 
 '18', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '16', 
 'nikul@calmease.com'),
('Breathing Techniques for Relaxation', 
 'Learn effective breathing techniques to relax and calm your mind.', 
 '2024-08-11', 
 '17:00:00', 
 '45', 
 '2024-08-11 17:00:00', 
 'scheduled', 
 '19', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '16', 
 'nikul@calmease.com'),

('Guided Meditation for Anxiety', 
 'A guided session focused on alleviating anxiety through meditation.', 
 '2024-08-11', 
 '15:30:00', 
 '35', 
 '2024-08-11 15:30:00', 
 'scheduled', 
 '20', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '16', 
 'nikul@calmease.com'),

('Afternoon Peaceful Meditation', 
 'Take a break in the afternoon to recharge with peaceful meditation.', 
 '2024-08-12', 
 '14:00:00', 
 '25', 
 '2024-08-12 14:00:00', 
 'scheduled', 
 '21', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '16', 
 'nikul@calmease.com');


INSERT INTO `calmease`.`meditation`
(`title`,
 `category`,
 `description`,
 `created_at`,
 `updated_at`,
 `duration`,
 `media_type`,
 `content_url`,
 `user_id`,
 `user_name`,
 `user_email`)
VALUES
('Morning Mindfulness Meditation', 
 'Mindfulness', 
 'Start your day with a calming and focused mindfulness meditation.', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '20', 
 'video', 
 'https://example.com/videos/morning-mindfulness.mp4', 
 '16', 
 'Nikul Kukadiya', 
 'nikul@calmease.com'),

('Evening Calm Meditation', 
 'Relaxation', 
 'Unwind at the end of the day with this calming guided meditation.', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '25', 
 'video', 
 'https://example.com/videos/evening-calm.mp4', 
 '16', 
 'Nikul Kukadiya', 
 'nikul@calmease.com'),

('Deep Breathing Techniques', 
 'Breathing', 
 'Learn and practice deep breathing techniques to reduce stress.', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '15', 
 'video', 
 'https://example.com/videos/deep-breathing.mp4', 
 '16', 
 'Nikul Kukadiya', 
 'nikul@calmease.com'),

('Guided Meditation for Anxiety', 
 'Anxiety Relief', 
 'A guided meditation session to help alleviate anxiety and promote calm.', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '30', 
 'video', 
 'https://example.com/videos/anxiety-guided-meditation.mp4', 
 '16', 
 'Nikul Kukadiya', 
 'nikul@calmease.com'),

('Afternoon Energy Boost Meditation', 
 'Energy', 
 'Recharge your energy levels with this quick and refreshing meditation.', 
 CURRENT_TIMESTAMP, 
 CURRENT_TIMESTAMP, 
 '10', 
 'video', 
 'https://example.com/videos/afternoon-energy-boost.mp4', 
 '16', 
 'Nikul Kukadiya', 
 'nikul@calmease.com');

INSERT INTO `calmease`.`meditation`
( `image_url`, `title`, `category`, `description`, `created_at`, `updated_at`, `duration`, `media_type`, `content_url`, `user_id`, `user_name`, `user_email`, `rating`, `vocal`, `sessiontime`)
VALUES
( 'https://libraryitems.insighttimer.com/d7w3j7p4u1v0u2b6e8w2y0k7z4m1n9g8t2s1g5e0/pictures/tiny_rectangle_medium.jpeg', 'Morning Mindfulness Meditation', 'Mindfulness', 'A calming mindfulness meditation to start your day.', '2024-11-15T08:18:39.000Z', '2024-11-15T08:18:39.000Z', '20 minutes', 'audio', 'KHFyUGCdvB4', 1, 'John Doe', 'johndoe@example.com', 1, 'Female only', 'Long'),
( 'https://libraryitems.insighttimer.com/j4z6g8n4h0y6f5t4u6n7p5t2e9w4c5d4g0n5a9s5/pictures/tiny_rectangle_medium.jpeg', 'Yoga Nidra For Sleep', 'Guided', 'Deep sleep guided meditation.', '2024-11-15T08:18:39.000Z', '2024-11-15T08:18:39.000Z', '22 minutes', 'audio', '4zZLsZmxiEM', 2, 'Jane Smith', 'janesmith@example.com', 4.5, 'Male only', 'Long'),
( 'https://libraryitems.insighttimer.com/p2t1j0w3q8p0w0m6j8s9g0f0k4h9f1f0h1z7j7l2/pictures/tiny_rectangle_medium.jpeg', 'Deep Sleep Guided Meditation', 'Guided', 'A calming guided meditation for deep sleep.', '2024-11-15T08:18:39.000Z', '2024-11-15T08:18:39.000Z', '61 minutes', 'audio', 'ft-vhYwHzxw', 3, 'Anna Lee', 'annalee@example.com', 2, 'Female Only', 'Extended'),
( 'https://libraryitems.insighttimer.com/x4w1f1h9q3m6b0w4r7t7r6k7v8n8c1d5r7q1q7g6/pictures/tiny_rectangle_medium.jpeg', 'Breathing Into Sleep', 'Guided', 'Focus on breathing to ease into sleep.', '2024-11-15T08:18:39.000Z', '2024-11-15T08:18:39.000Z', '17 minutes', 'audio', '2Co5D1i9vdM', 4, 'Michael Brown', 'michaelbrown@example.com', 3.5, 'Vocals', 'Short'),
( 'https://libraryitems.insighttimer.com/s8x9t4g8b5m0v9m7f5q0t9z2s5a7g2z4f6q6n5n6/pictures/tiny_rectangle_medium.jpeg', 'Deep Healing', 'Guided', 'Guided meditation for deep healing.', '2024-11-15T08:18:39.000Z', '2024-11-15T08:18:39.000Z', '22 minutes', 'audio', 'QHkXvPq2pQE', 5, 'Emily Clark', 'emilyclark@example.com', 2, 'Male only', 'Long');
good_memories





















-----------------------------------------------------------------


INSERT INTO `calm_essence`.`session`
(
 `title`,
 `description`,
 `session_date`,
 `session_time`,
 `actual_start_time`,
 `status`,
 `agora_channel_id`,
 `created_at`,
 `updated_at`,
 `expert_id`,
 `expert_email`,
 `duration`)
VALUES
( 'Morning Mindfulness Meditation', 'Start your day with a refreshing mindfulness session.', '2024-08-10', '06:30:00', '2024-08-10 06:30:00', 'started', '13', '2024-07-31 08:00:00', '2024-07-31 08:00:00', '15', 'Nikul+expert@gmail.com', '30'),
( 'Evening Calm Meditation', 'End your day with a calming meditation to release stress.', '2024-08-10', '20:00:00', '2024-08-10 20:00:00', 'started', '14', '2024-07-31 08:05:00', '2024-07-31 08:05:00', '15', 'Nikul+expert@gmail.com', '40'),
( 'Breathing Techniques for Relaxation', 'Learn effective breathing techniques to relax and calm your mind.', '2024-08-11', '17:00:00', '2024-08-11 17:00:00', 'started', '15', '2024-07-31 08:10:00', '2024-07-31 08:10:00', '15', 'Nikul+expert@gmail.com', '45'),
('Guided Meditation for Anxiety', 'A guided session focused on alleviating anxiety through meditation.', '2024-08-11', '15:30:00', '2024-08-11 15:30:00', 'started', '16', '2024-07-31 08:15:00', '2024-07-31 08:15:00', '15', 'Nikul+expert@gmail.com', '35'),
( 'Afternoon Peaceful Meditation', 'Take a break in the afternoon to recharge with peaceful meditation.', '2024-08-12', '14:00:00', '2024-08-12 14:00:00', 'started', '17', '2024-07-31 08:20:00', '2024-07-31 08:20:00', '15', 'Nikul+expert@gmail.com', '25');

-- Video media type examples
INSERT INTO `breathing` (title, description, created_at, updated_at, duration, image, media_type, content_url, user_id, user_name, user_email)
VALUES
    ('Morning Breathing Exercise', 'Start your day with a refreshing breathing session to energize yourself.', '2023-06-01 08:00:00', '2023-06-01 08:00:00', '10 minutes', 'https://st4.depositphotos.com/29770446/37771/v/450/depositphotos_377711466-stock-illustration-beautiful-young-woman-cartoon-character.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Midday Relaxation Breathing', 'Take a break in the middle of the day and unwind with this calming breathing session.', '2023-06-01 12:00:00', '2023-06-01 12:00:00', '15 minutes', 'https://us.123rf.com/450wm/exponoshoot/exponoshoot2203/exponoshoot220300007/182934626-a-man-meditates-in-the-lotus-position-against-the-background-of-the-leaves-of-the-tropical-plant.jpg?ver=6', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Evening Stress Relief Breathing', 'End your day on a peaceful note with this breathing session designed to release stress and tension.', '2023-06-01 18:00:00', '2023-06-01 18:00:00', '20 minutes', 'https://www.drmayankshukla.com/wp-content/uploads/2021/06/breathing-exercises-for-stress-management.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Relaxing Sunset Breathing', 'Witness the beauty of nature\'s transition while practicing deep breathing to unwind.', '2023-06-01 19:00:00', '2023-06-01 19:00:00', '15 minutes', 'https://img.freepik.com/premium-photo/woman-meditating-front-sunset_68067-4577.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Calm Ocean Breathing', 'Imagine the soothing sounds of ocean waves as you engage in this peaceful breathing exercise.', '2023-06-01 20:00:00', '2023-06-01 20:00:00', '20 minutes', 'https://t3.ftcdn.net/jpg/06/43/07/12/360_F_643071289_I60pgWvCavlK0pswqkibpeYudr0HY9Ft.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Mountain Serenity Breathing', 'Transport yourself to a tranquil mountain setting and practice deep breathing to connect with nature.', '2023-06-01 21:00:00', '2023-06-01 21:00:00', '25 minutes', 'https://img.freepik.com/premium-vector/vector-illustration-cartoon-style-featuring-man-meditating-mountain_549515-976.jpg?w=360', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Forest Harmony Breathing', 'Immerse yourself in the sounds of the forest while practicing mindful breathing to find inner peace.', '2023-06-01 22:00:00', '2023-06-01 22:00:00', '20 minutes', 'https://i.pinimg.com/736x/9f/e4/5b/9fe45bc47f1da16b89fd859ee8bbce8c.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Soothing Rain Breathing', 'Let the gentle rhythm of rainfall guide your breath in this relaxing breathing session.', '2023-06-01 23:00:00', '2023-06-01 23:00:00', '15 minutes', 'https://img.freepik.com/premium-photo/mental-health-illustration-meditation-inner-peace-vibrant-color-psychology-cartoon_1003829-60509.jpg?w=360', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Peaceful Meadow Breathing', 'Picture yourself in a tranquil meadow and engage in calming breathing exercises to find serenity.', '2023-06-01 07:00:00', '2023-06-01 07:00:00', '20 minutes', 'https://img.freepik.com/premium-photo/cartoon-girl-meditating-with-her-eyes-closed-her-eyes-closed-her-eyes-closed_900775-13689.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com');

-- Text media type examples
INSERT INTO `breathing` (title, description, created_at, updated_at, duration, image, media_type, text_content, user_id, user_name, user_email)
VALUES
    ('Deep Breathing for Relaxation', 'Follow these steps for a calming deep breathing exercise.', '2023-06-01 09:00:00', '2023-06-01 09:00:00', '15 minutes', 'https://example.com/images/deep_breathing.jpg', 'text', '1. Sit comfortably. 2. Close your eyes. 3. Inhale deeply through your nose for 4 seconds. 4. Hold your breath for 7 seconds. 5. Exhale slowly through your mouth for 8 seconds.', 1, 'Unnati', 'unnati@example.com'),
    
    ('Morning Breathing Routine', 'A detailed guide to start your day with mindful breathing.', '2023-06-01 10:00:00', '2023-06-01 10:00:00', '10 minutes', 'https://example.com/images/morning_routine.jpg', 'text', '1. Find a quiet place. 2. Sit or lie down comfortably. 3. Breathe in slowly through your nose. 4. Hold your breath for a moment. 5. Breathe out slowly through your mouth.', 1, 'Unnati', 'unnati@example.com'),
    
    ('Evening Wind Down', 'Unwind your day with these step-by-step breathing exercises.', '2023-06-01 20:00:00', '2023-06-01 20:00:00', '20 minutes', 'https://example.com/images/evening_wind_down.jpg', 'text', '1. Sit in a comfortable position. 2. Focus on your breathing. 3. Inhale slowly for 4 seconds. 4. Exhale slowly for 6 seconds. 5. Repeat until relaxed.', 1, 'Unnati', 'unnati@example.com'),
    
    ('Stress Relief Breathing', 'Practice these steps to relieve stress effectively.', '2023-06-01 15:00:00', '2023-06-01 15:00:00', '20 minutes', 'https://example.com/images/stress_relief.jpg', 'text', '1. Take a deep breath in through your nose. 2. Hold your breath for a few seconds. 3. Breathe out slowly through your mouth. 4. Repeat as necessary.', 1, 'Unnati', 'unnati@example.com');








INSERT INTO articles (title, content, user_id, user_name, user_email, image, tags) VALUES
('Introduction to MySQL', '<p>MySQL is an open-source relational database management system.</p>', 1, 'Unnati Kapadia', 'unnati@example.com', 'https://example.com/images/mysql.png', 'database,mysql,sql'),
('Learning Vue.js', '<p>Vue.js is a progressive JavaScript framework for building user interfaces.</p>', 1, 'Unnati Kapadia', 'unnati@example.com', 'https://example.com/images/vue.png', 'javascript,vue,frontend'),
('Mastering Bootstrap', '<p>Bootstrap is a free and open-source CSS framework directed at responsive, mobile-first front-end web development.</p>', 1, 'Unnati Kapadia', 'unnati@example.com', 'https://example.com/images/bootstrap.png', 'css,bootstrap,frontend'),
('Getting Started with Ionic', '<p>Ionic is a complete open-source SDK for hybrid mobile app development.</p>', 1, 'Unnati Kapadia', 'unnati@example.com', 'https://example.com/images/ionic.png', 'ionic,mobile,web'),
('Understanding Google Cloud Functions', '<p>Google Cloud Functions is a serverless execution environment for building and connecting cloud services.</p>', 1, 'Unnati Kapadia', 'unnati@example.com', 'https://example.com/images/gcf.png', 'google-cloud,cloud-functions,cloud');

UPDATE articles
SET image = 'https://blogassets.leverageedu.com/blog/wp-content/uploads/2020/01/24145013/article-writing.jpg'
WHERE article_id != 10;





















CREATE TABLE `chat` (
    `chat_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id1` VARCHAR(255) NOT NULL,
    `user_id2` VARCHAR(255) NOT NULL,
    `user1_name` VARCHAR(255) NOT NULL,
    `user2_name` VARCHAR(255) NOT NULL,
    `is_chat_accepted` BOOLEAN DEFAULT FALSE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `last_message_text` TEXT
);


CREATE TABLE `message` (
    `message_id` INT AUTO_INCREMENT PRIMARY KEY,
    `chat_id` INT NOT NULL,
    `sender_id` VARCHAR(255) NOT NULL,
    `message_text` TEXT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`chat_id`) REFERENCES `chat`(`chat_id`)
);






-- Inserting 2 experts
INSERT INTO `users` (`email`, `full_name`, `phone_number`, `user_type`, `password`) VALUES
('unanti@example.com', 'Unanti', '111-222-3333', 'expert', 'password123'),
('nikul@example.com', 'Nikul', '444-555-6666', 'expert', 'password123');

-- Inserting 3 users (including Vishesh)
INSERT INTO `users` (`email`, `full_name`, `phone_number`, `user_type`, `password`) VALUES
('avadh@example.com', 'Avadh', '777-888-9999', 'user', 'password123'),
('rushit@example.com', 'Rushit', '000-111-2222', 'user', 'password123'),
('vishesh@example.com', 'Vishesh', '333-444-5555', 'user', 'password123');



-- Create chats between experts and users
INSERT INTO `chat` (`user_id1`, `user_id2`, `user1_name`, `user2_name`, `is_chat_accepted`, `last_message_text`) VALUES
('unanti@example.com', 'avadh@example.com', 'Unanti', 'Avadh', TRUE, 'Hello Avadh, how can I assist you today?'),
('unanti@example.com', 'rushit@example.com', 'Unanti', 'Rushit', TRUE, 'Hello Rushit, how can I assist you today?'),
('unanti@example.com', 'vishesh@example.com', 'Unanti', 'Vishesh', TRUE, 'Hello Vishesh, how can I assist you today?'),
('nikul@example.com', 'avadh@example.com', 'Nikul', 'Avadh', TRUE, 'Hello Avadh, ready to discuss?'),
('nikul@example.com', 'rushit@example.com', 'Nikul', 'Rushit', TRUE, 'Hello Rushit, ready to discuss?'),
('nikul@example.com', 'vishesh@example.com', 'Nikul', 'Vishesh', TRUE, 'Hello Vishesh, ready to discuss?');



-- Messages for chat between Unanti and Avadh
INSERT INTO `message` (`chat_id`, `sender_id`, `message_text`) VALUES
(1, 'unanti@example.com', 'Hello Avadh, how can I assist you today?'),
(1, 'avadh@example.com', 'Hi Unanti, I need help with my account.'),
(1, 'unanti@example.com', 'Sure, let me help you with that.');

-- Messages for chat between Unanti and Rushit
INSERT INTO `message` (`chat_id`, `sender_id`, `message_text`) VALUES
(2, 'unanti@example.com', 'Hello Rushit, how can I assist you today?'),
(2, 'rushit@example.com', 'Hi Unanti, I need assistance with a project.'),
(2, 'unanti@example.com', 'Absolutely, what’s the issue with your project?');

-- Messages for chat between Unanti and Vishesh
INSERT INTO `message` (`chat_id`, `sender_id`, `message_text`) VALUES
(3, 'unanti@example.com', 'Hello Vishesh, how can I assist you today?'),
(3, 'vishesh@example.com', 'Hi Unanti, I have a question about my account.'),
(3, 'unanti@example.com', 'Sure, what’s your question?');

-- Messages for chat between Nikul and Avadh
INSERT INTO `message` (`chat_id`, `sender_id`, `message_text`) VALUES
(4, 'nikul@example.com', 'Hello Avadh, ready to discuss?'),
(4, 'avadh@example.com', 'Yes, I am ready. What do you need from me?'),
(4, 'nikul@example.com', 'I need some details about your last task.');

-- Messages for chat between Nikul and Rushit
INSERT INTO `message` (`chat_id`, `sender_id`, `message_text`) VALUES
(5, 'nikul@example.com', 'Hello Rushit, ready to discuss?'),
(5, 'rushit@example.com', 'Yes, I am available. What do you need?'),
(5, 'nikul@example.com', 'I need some information about your recent issue.');

-- Messages for chat between Nikul and Vishesh
INSERT INTO `message` (`chat_id`, `sender_id`, `message_text`) VALUES
(6, 'nikul@example.com', 'Hello Vishesh, ready to discuss?'),
(6, 'vishesh@example.com', 'Yes, what do you need help with?'),
(6, 'nikul@example.com', 'I need some information about your recent task.');

#fetch all the available chats for user 
SELECT * 
FROM `chat`
WHERE `user_id1` = 'nikul@example.com' 
   OR `user_id2` = 'nikul@example.com';
   
-- One to one chats for chat id    
 SELECT * 
FROM `message`
WHERE `chat_id` = 4
ORDER BY `created_at` ASC;
  
-- fetch all the expert as per search via name
SELECT * 
FROM `users`
WHERE `user_type` = 'expert' 
  AND `full_name` LIKE '%ni%';

-- Create chat request
INSERT INTO `chat` (`user_id1`, `user_id2`, `user1_name`, `user2_name`, `is_chat_accepted`, `last_message_text`) VALUES
('unanti@example.com', 'avadh@example.com', 'Unanti', 'Avadh', TRUE, 'Hello Avadh, how can I assist you today?');


-- Create message or send message 
INSERT INTO `message` (`chat_id`, `sender_id`, `message_text`) VALUES
(6, 'nikul@example.com', 'Hello Vishesh, ready to discuss?');



CREATE TABLE `session` (
    `session_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `session_date` DATE NOT NULL,
    `session_time` TIME NOT NULL,
    `actual_start_time` DATETIME,
    `status` ENUM('scheduled', 'started', 'completed') DEFAULT 'scheduled',
    `agora_channel_id` VARCHAR(255) NOT NULL UNIQUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `expert_id` VARCHAR(255) NOT NULL,
    `expert_email` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`expert_email`) REFERENCES `users`(`email`)
);

Select * from session;
Select * from session_token;

CREATE TABLE session_token (
    token_id INT AUTO_INCREMENT PRIMARY KEY,
    session_id INT NOT NULL,
	user_id INT NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES session(session_id),
    FOREIGN KEY (user_email) REFERENCES users(email)
);



select *from  `breathing`;



CREATE TABLE `breathing` (
    `breathing_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `duration` VARCHAR(50),
    `image` VARCHAR(255),
    `text_content` VARCHAR(255),
    `media_type` ENUM('video', 'audio', 'text') NOT NULL,
    `content_url` VARCHAR(255),
    `user_id` INT,
    `user_name` VARCHAR(255),
    `user_email` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`) -- Assuming a `users` table exists with `user_id` as the primary key
);


-- Video media type examples
INSERT INTO `breathing` (title, description, created_at, updated_at, duration, image, media_type, content_url, user_id, user_name, user_email)
VALUES
    ('Morning Breathing Exercise', 'Start your day with a refreshing breathing session to energize yourself.', '2023-06-01 08:00:00', '2023-06-01 08:00:00', '10 minutes', 'https://st4.depositphotos.com/29770446/37771/v/450/depositphotos_377711466-stock-illustration-beautiful-young-woman-cartoon-character.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Midday Relaxation Breathing', 'Take a break in the middle of the day and unwind with this calming breathing session.', '2023-06-01 12:00:00', '2023-06-01 12:00:00', '15 minutes', 'https://us.123rf.com/450wm/exponoshoot/exponoshoot2203/exponoshoot220300007/182934626-a-man-meditates-in-the-lotus-position-against-the-background-of-the-leaves-of-the-tropical-plant.jpg?ver=6', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Evening Stress Relief Breathing', 'End your day on a peaceful note with this breathing session designed to release stress and tension.', '2023-06-01 18:00:00', '2023-06-01 18:00:00', '20 minutes', 'https://www.drmayankshukla.com/wp-content/uploads/2021/06/breathing-exercises-for-stress-management.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Relaxing Sunset Breathing', 'Witness the beauty of nature\'s transition while practicing deep breathing to unwind.', '2023-06-01 19:00:00', '2023-06-01 19:00:00', '15 minutes', 'https://img.freepik.com/premium-photo/woman-meditating-front-sunset_68067-4577.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Calm Ocean Breathing', 'Imagine the soothing sounds of ocean waves as you engage in this peaceful breathing exercise.', '2023-06-01 20:00:00', '2023-06-01 20:00:00', '20 minutes', 'https://t3.ftcdn.net/jpg/06/43/07/12/360_F_643071289_I60pgWvCavlK0pswqkibpeYudr0HY9Ft.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Mountain Serenity Breathing', 'Transport yourself to a tranquil mountain setting and practice deep breathing to connect with nature.', '2023-06-01 21:00:00', '2023-06-01 21:00:00', '25 minutes', 'https://img.freepik.com/premium-vector/vector-illustration-cartoon-style-featuring-man-meditating-mountain_549515-976.jpg?w=360', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Forest Harmony Breathing', 'Immerse yourself in the sounds of the forest while practicing mindful breathing to find inner peace.', '2023-06-01 22:00:00', '2023-06-01 22:00:00', '20 minutes', 'https://i.pinimg.com/736x/9f/e4/5b/9fe45bc47f1da16b89fd859ee8bbce8c.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Soothing Rain Breathing', 'Let the gentle rhythm of rainfall guide your breath in this relaxing breathing session.', '2023-06-01 23:00:00', '2023-06-01 23:00:00', '15 minutes', 'https://img.freepik.com/premium-photo/mental-health-illustration-meditation-inner-peace-vibrant-color-psychology-cartoon_1003829-60509.jpg?w=360', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com'),
    
    ('Peaceful Meadow Breathing', 'Picture yourself in a tranquil meadow and engage in calming breathing exercises to find serenity.', '2023-06-01 07:00:00', '2023-06-01 07:00:00', '20 minutes', 'https://img.freepik.com/premium-photo/cartoon-girl-meditating-with-her-eyes-closed-her-eyes-closed-her-eyes-closed_900775-13689.jpg', 'video', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 1, 'Unnati', 'unnati@example.com');

-- Text media type examples
INSERT INTO `breathing` (title, description, created_at, updated_at, duration, image, media_type, text_content, user_id, user_name, user_email)
VALUES
    ('Deep Breathing for Relaxation', 'Follow these steps for a calming deep breathing exercise.', '2023-06-01 09:00:00', '2023-06-01 09:00:00', '15 minutes', 'https://example.com/images/deep_breathing.jpg', 'text', '1. Sit comfortably. 2. Close your eyes. 3. Inhale deeply through your nose for 4 seconds. 4. Hold your breath for 7 seconds. 5. Exhale slowly through your mouth for 8 seconds.', 1, 'Unnati', 'unnati@example.com'),
    
    ('Morning Breathing Routine', 'A detailed guide to start your day with mindful breathing.', '2023-06-01 10:00:00', '2023-06-01 10:00:00', '10 minutes', 'https://example.com/images/morning_routine.jpg', 'text', '1. Find a quiet place. 2. Sit or lie down comfortably. 3. Breathe in slowly through your nose. 4. Hold your breath for a moment. 5. Breathe out slowly through your mouth.', 1, 'Unnati', 'unnati@example.com'),
    
    ('Evening Wind Down', 'Unwind your day with these step-by-step breathing exercises.', '2023-06-01 20:00:00', '2023-06-01 20:00:00', '20 minutes', 'https://example.com/images/evening_wind_down.jpg', 'text', '1. Sit in a comfortable position. 2. Focus on your breathing. 3. Inhale slowly for 4 seconds. 4. Exhale slowly for 6 seconds. 5. Repeat until relaxed.', 1, 'Unnati', 'unnati@example.com'),
    
    ('Stress Relief Breathing', 'Practice these steps to relieve stress effectively.', '2023-06-01 15:00:00', '2023-06-01 15:00:00', '20 minutes', 'https://example.com/images/stress_relief.jpg', 'text', '1. Take a deep breath in through your nose. 2. Hold your breath for a few seconds. 3. Breathe out slowly through your mouth. 4. Repeat as necessary.', 1, 'Unnati', 'unnati@example.com');



SELECT 
    TABLE_NAME AS referring_table,
    COLUMN_NAME AS referring_column,
    CONSTRAINT_NAME AS foreign_key_constraint_name
FROM 
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE 
    REFERENCED_TABLE_NAME = 'users'
    AND REFERENCED_COLUMN_NAME = 'email'
    AND TABLE_SCHEMA = 'calm_essence';  -- Replace 'your_database_name' with your actual database name


-- Drop the foreign key constraint from the `session` table
ALTER TABLE `session`
DROP FOREIGN KEY `session_ibfk_1`;

-- Drop the foreign key constraint from the `session_token` table
ALTER TABLE `session_token`
DROP FOREIGN KEY `session_token_ibfk_2`;


-- Add the foreign key constraint back to the `session` table
ALTER TABLE `session`
ADD CONSTRAINT `session_ibfk_1`
FOREIGN KEY (`expert_email`) REFERENCES `users`(`email`);

-- Add the foreign key constraint back to the `session_token` table
ALTER TABLE `session_token`
ADD CONSTRAINT `session_token_ibfk_2`
FOREIGN KEY (`user_email`) REFERENCES `users`(`email`);


drop table breathing;
































Select * from users;

use calm_essence;

-- article_id <= 12

INSERT INTO `calm_essence`.`articles` ( `title`, `content`, `created_at`, `updated_at`, `user_id`, `user_name`, `user_email`, `image`, `tags`) VALUES
('The Art of Mindful Breathing', '<p>Mindful breathing is the cornerstone of any meditation practice. By focusing on the breath, we bring our attention to the present moment, helping to reduce stress and anxiety. This simple yet powerful technique allows us to reconnect with our inner self and find peace in the midst of chaos. Whether you are a beginner or an experienced meditator, mindful breathing is a practice that can be done anywhere, at any time.</p><p>Start by finding a quiet place to sit comfortably. Close your eyes and take a deep breath in through your nose, allowing your abdomen to rise. Exhale slowly through your mouth, letting go of any tension in your body. Continue to breathe deeply and slowly, focusing your attention on the sensation of the breath as it enters and leaves your body. If your mind starts to wander, gently bring your focus back to the breath.</p>', '2024-07-27 23:21:04', '2024-07-31 15:58:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1553524558-30b99f2f2f94', 'meditation, breathing, mindfulness'),
( 'Guided Meditation for Deep Relaxation', '<p>Guided meditation is a powerful tool for achieving deep relaxation and inner peace. In this article, we explore a step-by-step guided meditation that will help you release tension and stress from your body and mind. This practice is ideal for anyone looking to unwind after a long day or to prepare for a restful night\'s sleep.</p><p>Begin by sitting or lying down in a comfortable position. Close your eyes and take a few deep breaths, allowing your body to relax with each exhale. Imagine a warm, golden light surrounding your body, filling you with a sense of calm and tranquility. As you continue to breathe deeply, visualize the light spreading throughout your body, releasing any tension or stress you may be holding onto.</p>', '2024-07-27 23:22:04', '2024-07-31 15:59:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1553531384-cc64dd49f78d', 'meditation, relaxation, guided meditation'),
( 'The Benefits of Daily Meditation Practice', '<p>Daily meditation practice can have a profound impact on your overall well-being. From reducing stress and anxiety to improving concentration and emotional resilience, the benefits of meditation are numerous and well-documented. In this article, we explore the key benefits of making meditation a part of your daily routine and provide tips on how to get started.</p><p>One of the most significant benefits of daily meditation is its ability to reduce stress. By taking a few moments each day to focus on your breath and quiet your mind, you can create a sense of calm that carries over into the rest of your day. Additionally, meditation has been shown to improve emotional regulation, helping you to respond to challenges with greater patience and clarity.</p>', '2024-07-28 08:30:04', '2024-07-31 16:00:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1506214377099-90c9c5f43c35', 'meditation, daily practice, benefits'),
( 'Breathing Techniques for Stress Relief', '<p>Breathing techniques are a simple yet effective way to manage stress and promote relaxation. In this article, we will explore several breathing exercises that you can incorporate into your daily routine to help you stay calm and focused.</p><p>One of the most popular breathing techniques for stress relief is deep diaphragmatic breathing. This technique involves taking slow, deep breaths from the diaphragm, which helps to activate the body\'s relaxation response. To practice diaphragmatic breathing, sit or lie down in a comfortable position and place one hand on your abdomen. As you inhale, allow your abdomen to rise, and as you exhale, let it fall. Repeat this process for a few minutes, focusing on the sensation of the breath.</p>', '2024-07-28 10:45:04', '2024-07-31 16:01:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330', 'breathing, stress relief, techniques'),
( 'Meditation for Beginners: A Simple Guide', '<p>Meditation can seem daunting for beginners, but with the right guidance, it can become a simple and rewarding practice. In this article, we provide a step-by-step guide to help you get started with meditation, including tips on finding a comfortable position, focusing your mind, and creating a consistent practice.</p><p>Start by finding a quiet place where you won\'t be disturbed. Sit or lie down in a comfortable position and close your eyes. Begin by taking a few deep breaths, allowing your body to relax with each exhale. As you continue to breathe deeply, focus your attention on your breath, noticing the sensation as it enters and leaves your body. If your mind starts to wander, gently bring your focus back to the breath.</p>', '2024-07-28 12:00:04', '2024-07-31 16:02:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1528222354212-a295f02f92ff', 'meditation, beginners, guide'),
( 'Cultivating Calmness Through Meditation', '<p>Calmness is a state of mind that can be cultivated through regular meditation practice. In this article, we explore how meditation can help you develop a calm and peaceful mindset, even in the midst of life\'s challenges. We will also provide practical tips on how to incorporate meditation into your daily routine to achieve a greater sense of calm.</p><p>One of the key benefits of meditation is its ability to quiet the mind and reduce the impact of stress. By focusing on the present moment and letting go of worries about the past or future, you can create a sense of calm that permeates your entire being. Regular meditation practice can also help you develop greater emotional resilience, allowing you to navigate life\'s challenges with greater ease and grace.</p>', '2024-07-28 14:15:04', '2024-07-31 16:03:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1475232293689-accb7ee45b76', 'meditation, calmness, mindfulness'),
( 'The Science Behind Meditation', '<p>While meditation has been practiced for thousands of years, it is only in recent decades that science has begun to uncover its many benefits. In this article, we explore the scientific research behind meditation and how it affects the brain and body. From reducing stress and anxiety to improving focus and emotional regulation, the science behind meditation is both fascinating and compelling.</p><p>Research has shown that meditation can lead to changes in the brain\'s structure and function. For example, regular meditation practice has been found to increase the thickness of the prefrontal cortex, a part of the brain associated with attention and decision-making. Additionally, meditation has been shown to reduce the activity of the amygdala, a region of the brain involved in the stress response.</p>', '2024-07-28 16:30:04', '2024-07-31 16:04:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1513553404607-988bf2703777', 'meditation, science, research'),
( 'Finding Inner Peace Through Meditation', '<p>In a world filled with constant noise and distractions, finding inner peace can seem like an impossible task. However, meditation offers a pathway to inner tranquility that is accessible to everyone. In this article, we explore how meditation can help you find inner peace and provide tips on how to make meditation a regular part of your life.</p><p>Inner peace is not something that can be achieved overnight, but with regular meditation practice, it is possible to cultivate a deep sense of calm and contentment. By taking time each day to sit in stillness and focus on your breath, you can create a space of peace within yourself that is unaffected by external circumstances. This inner peace can then be carried with you throughout your day, helping you to navigate life\'s challenges with greater ease and grace.</p>', '2024-07-28 18:45:04', '2024-07-31 16:05:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1466626432011-021699b9442b', 'meditation, inner peace, tranquility'),
( 'The Role of Breathing in Meditation', '<p>Breathing plays a crucial role in meditation, serving as both an anchor for the mind and a tool for relaxation. In this article, we explore the connection between breathing and meditation and provide practical tips on how to use your breath to enhance your meditation practice.</p><p>One of the simplest and most effective ways to use your breath in meditation is to focus on it as an anchor for your attention. By bringing your awareness to the sensation of the breath as it enters and leaves your body, you can create a sense of grounding and stability that helps to calm the mind. Additionally, deep, slow breathing can activate the body\'s relaxation response, helping to reduce stress and anxiety.</p>', '2024-07-28 20:00:04', '2024-07-31 16:06:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1507652955-f3dcef5a3be5', 'breathing, meditation, relaxation'),
( 'Creating a Meditation Space at Home', '<p>Creating a dedicated meditation space at home can help you establish a consistent practice and deepen your meditation experience. In this article, we explore how to design a meditation space that is both calming and inspiring, including tips on choosing the right location, decor, and lighting.</p><p>Your meditation space should be a place where you feel comfortable and at ease. Choose a location that is quiet and free from distractions, and consider adding elements such as soft lighting, comfortable seating, and calming decor. You may also want to incorporate items that inspire you, such as candles, crystals, or artwork. By creating a space that feels inviting and peaceful, you can make meditation a regular and enjoyable part of your daily routine.</p>', '2024-07-28 22:15:04', '2024-07-31 16:07:02', '1', 'Unnati Kapadia', 'unnatikapadia7653@gmail.com', 'https://images.unsplash.com/photo-1499083097717-e84f9166340f', 'meditation, space, home, design');

delete from articles where article_id <= 12;
Select * from articles;













Select * from users;
 
DELETE FROM session_token WHERE session_id <= 12;
DELETE FROM session WHERE session_id <= 12;

Select * from session;
SELECT `session_id`, `title`, `description`, `session_date`, `session_time`, `actual_start_time`, `status`, `agora_channel_id`, `created_at`, `updated_at`, `expert_id`, `expert_email`, `duration` FROM `calm_essence`.`session`;

INSERT INTO `calm_essence`.`session`
(
 `title`,
 `description`,
 `session_date`,
 `session_time`,
 `actual_start_time`,
 `status`,
 `agora_channel_id`,
 `created_at`,
 `updated_at`,
 `expert_id`,
 `expert_email`,
 `duration`)
VALUES
( 'Morning Mindfulness Meditation', 'Start your day with a refreshing mindfulness session.', '2024-08-10', '06:30:00', '2024-08-10 06:30:00', 'started', '13', '2024-07-31 08:00:00', '2024-07-31 08:00:00', '15', 'Nikul+expert@gmail.com', '30'),
( 'Evening Calm Meditation', 'End your day with a calming meditation to release stress.', '2024-08-10', '20:00:00', '2024-08-10 20:00:00', 'started', '14', '2024-07-31 08:05:00', '2024-07-31 08:05:00', '15', 'Nikul+expert@gmail.com', '40'),
( 'Breathing Techniques for Relaxation', 'Learn effective breathing techniques to relax and calm your mind.', '2024-08-11', '17:00:00', '2024-08-11 17:00:00', 'started', '15', '2024-07-31 08:10:00', '2024-07-31 08:10:00', '15', 'Nikul+expert@gmail.com', '45'),
('Guided Meditation for Anxiety', 'A guided session focused on alleviating anxiety through meditation.', '2024-08-11', '15:30:00', '2024-08-11 15:30:00', 'started', '16', '2024-07-31 08:15:00', '2024-07-31 08:15:00', '15', 'Nikul+expert@gmail.com', '35'),
( 'Afternoon Peaceful Meditation', 'Take a break in the afternoon to recharge with peaceful meditation.', '2024-08-12', '14:00:00', '2024-08-12 14:00:00', 'started', '17', '2024-07-31 08:20:00', '2024-07-31 08:20:00', '15', 'Nikul+expert@gmail.com', '25');

use calm_essence;

CREATE TABLE contactus (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);





