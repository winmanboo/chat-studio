# 版本追踪
- 20250514：支持 rag 基础检索能力（包括知识库、Web Search）
  - 支持初次聊天的会话标题更新
- 20250524：新增 chroma 向量存储，根据用户id支持向量数据隔离
- 20250525：
  - 修复 AdvancedMessageWindowChatMemory 超过maxMessage的消息查询错误
  - 用户使用rag检索时，消息列表接口去除检索信息展示（字符串替换方案，有缺陷）

# 目标
- [ ] Rerank 模型选型
- [x] rag 基础检索能力
- [x] Web Search 检索能力
- [ ] 深度思考（等待官方支持）
- [ ] 多模态
- [ ] 可配置化（llm 模型可配置化、向量模型可配置化）

# 已知 Bug
- [X] 去除增强检索模式下的扩展检索信息（当前实现是通过字符串替换形式实现，字符串替换方式会有点局限，需要优化）
- [ ] 调用 llm 重试失败后没有将这次 sse 断开，导致该连接一直处于重试状态下（前端设置一个超时时间，然后断开 sse 连接）

# 优化
- [ ] 文档切分（粒度）

# 框架部分
- [ ] langchain4j 目前使用的是 OpenAi 的标准输出格式，如果使用 DeepSeek R1 的模型，它的响应会多输出一个 reasoning_content 字段 
代表思维链（当前版本并不支持 deepseek 的输出）https://github.com/langchain4j/langchain4j/issues/2472

# 版本要求
- chroma 新版接口地址从 v1改为了 v2，但是langchain4j-chroma请求的是v1接口，所以只能使用老版本镜像chromadb/chroma:0.4.24,
chroma接口可以看ChromaApi.java

# 端口监听部分
- 应用：8080
- chroma：8000
- mysql：3306
- minio：9000
- mingodb：27017