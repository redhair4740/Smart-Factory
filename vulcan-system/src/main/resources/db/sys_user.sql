-- 创建系统用户表
CREATE TABLE sys_user (
    -- 主键ID
    id BIGSERIAL PRIMARY KEY,
    
    -- 用户基本信息
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    login_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    plant_code VARCHAR(50),
    
    -- 管理员标志
    super_admin_flag SMALLINT NOT NULL DEFAULT 0 CHECK (super_admin_flag IN (0, 1)),
    plant_admin_flag SMALLINT NOT NULL DEFAULT 0 CHECK (plant_admin_flag IN (0, 1)),
    
    -- 基础字段
    create_by VARCHAR(100),
    create_time TIMESTAMP,
    update_by VARCHAR(100),
    update_time TIMESTAMP,
    remark TEXT,
    status CHAR(1) DEFAULT '0' CHECK (status IN ('0', '1')),
    del_flag CHAR(1) DEFAULT '0' CHECK (del_flag IN ('0', '1')),
    version VARCHAR(20)
);

-- 创建索引
CREATE INDEX idx_sys_user_login_name ON sys_user(login_name);
CREATE INDEX idx_sys_user_phone ON sys_user(phone);
CREATE INDEX idx_sys_user_email ON sys_user(email);
CREATE INDEX idx_sys_user_plant_code ON sys_user(plant_code);

-- 添加表注释
COMMENT ON TABLE sys_user IS '系统用户表';

-- 添加字段注释
COMMENT ON COLUMN sys_user.code IS '用户编码';
COMMENT ON COLUMN sys_user.name IS '用户名称';
COMMENT ON COLUMN sys_user.login_name IS '登录名称';
COMMENT ON COLUMN sys_user.password IS '登录密码';
COMMENT ON COLUMN sys_user.phone IS '手机号码';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.plant_code IS '工厂编码';
COMMENT ON COLUMN sys_user.super_admin_flag IS '是否是超级管理员（0代表不是 1代表是）';
COMMENT ON COLUMN sys_user.plant_admin_flag IS '是否是工厂管理员（0代表不是 1代表是）';
COMMENT ON COLUMN sys_user.create_by IS '创建者';
COMMENT ON COLUMN sys_user.create_time IS '创建时间';
COMMENT ON COLUMN sys_user.update_by IS '更新者';
COMMENT ON COLUMN sys_user.update_time IS '更新时间';
COMMENT ON COLUMN sys_user.remark IS '备注';
COMMENT ON COLUMN sys_user.status IS '状态（0正常 1停用）';
COMMENT ON COLUMN sys_user.del_flag IS '删除标志（0代表存在 1代表删除）';
COMMENT ON COLUMN sys_user.version IS '版本号';