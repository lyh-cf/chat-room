/*
 Navicat Premium Data Transfer

 Source Server         : 数据库
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : qq

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 21/12/2022 21:22:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `idx_userfriend_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '朋友账号',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 327 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friend_application
-- ----------------------------
DROP TABLE IF EXISTS `friend_application`;
CREATE TABLE `friend_application`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_sender_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人账号',
  `idx_getter_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '被申请账号',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群名',
  `idx_group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群编号',
  `idx_group_logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/com/qqclient/view/pictures/群聊(1).png' COMMENT '群头像',
  `idx_created_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `idx_group_profile` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群简介',
  `idx_master_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群主账号',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_application
-- ----------------------------
DROP TABLE IF EXISTS `group_application`;
CREATE TABLE `group_application`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_sender_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送者账号',
  `idx_group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群编号',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_msg
-- ----------------------------
DROP TABLE IF EXISTS `group_msg`;
CREATE TABLE `group_msg`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群编号',
  `idx_sender_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送者账号',
  `idx_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '发送内容',
  `idx_send_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送时间',
  `idx_msg_type` int(0) NOT NULL COMMENT '消息类型(0表示文字，1表示图片)',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 170 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_user
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群编号',
  `idx_group_userid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群成员账号',
  `idx_group_state` int(0) NOT NULL DEFAULT 0 COMMENT '0表示成员，1表示管理员，2表示群主',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for private_msg
-- ----------------------------
DROP TABLE IF EXISTS `private_msg`;
CREATE TABLE `private_msg`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_sender_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送者账号',
  `idx_getter_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接受者账号',
  `idx_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '发送内容',
  `idx_send_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送时间',
  `idx_msg_type` int(0) NOT NULL COMMENT '消息类型(0表示文字，1表示图片)',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 392 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_common_phrases
-- ----------------------------
DROP TABLE IF EXISTS `user_common_phrases`;
CREATE TABLE `user_common_phrases`  (
  `pk_id` int unsigned NOT NULL COMMENT 'ID',
  `idx_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `idx_common_phrases` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '常用语',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `pk_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `idx_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `idx_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `idx_qqemail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'QQ邮箱',
  `idx_sex` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '男' COMMENT '性别',
  `idx_headimage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '/com/qqclient/view/pictures/默认头像.png' COMMENT '头像地址',
  `idx_age` int(0) DEFAULT 18 COMMENT '年龄',
  `idx_birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '2004-01-01' COMMENT '生日',
  `idx_personalizedsignature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '这个用户很懒，还没想好说什么' COMMENT '个性签名',
  `idx_state` int(0) DEFAULT 0 COMMENT '用户状态，0表示不在线，1表示在线',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
