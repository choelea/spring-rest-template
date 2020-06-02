/*
 * Copyright 2014-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.icoding.springrest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import javax.servlet.RequestDispatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.icoding.springrest.entities.Category;
import tech.icoding.springrest.repo.CategoryRepository;
import tech.icoding.springrest.repo.ProductRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDocumentation {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ObjectMapper objectMapper;

	private RestDocumentationResultHandler documentationHandler;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.documentationHandler = document("{method-name}",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(this.documentationHandler).build();
	}

	@Test
	public void errorExample() throws Exception {
		this.mockMvc
				.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
						.requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/categories")
						.requestAttr(RequestDispatcher.ERROR_MESSAGE,
								"The Category 'http://localhost:8080/categories/123' does not exist"))
				.andDo(print()).andExpect(status().isBadRequest()).andExpect(jsonPath("error", is("Bad Request")))
				.andExpect(jsonPath("timestamp", is(notNullValue()))).andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("path", is(notNullValue())))
				.andDo(document("error-example",
						responseFields(
								fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
								fieldWithPath("message").description("A description of the cause of the error"),
								fieldWithPath("path").description("The path to which the request was made"),
								fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
								fieldWithPath("timestamp")
										.description("The time, in milliseconds, at which the error occurred"))));
	}

	@Test
	public void categoryListExample() throws Exception {
		this.categoryRepository.deleteAll();

		createCategory("云计算");
		createCategory("管理系统");
		createCategory("视频监控");

		this.mockMvc.perform(get("/categories"))
		.andExpect(status().isOk())
		.andDo(this.documentationHandler.document(springPageRequestFields(),
				springPageResponseFields()));
			
				
	}

	private void createCategory(String name) {
		Category category = new Category();
		category.setName(name);
		categoryRepository.save(category);
	}

	private  ResponseFieldsSnippet springPageResponseFields() {
		return responseFields(
				subsectionWithPath("content").description("请求的资源的数组"),
				subsectionWithPath("pageable").description("分页请求信息"),
				subsectionWithPath("totalElements").description("总共多少条记录"),
				subsectionWithPath("totalPages").description("一共多少页"),
				subsectionWithPath("size").description("每页多少记录"),
				subsectionWithPath("number").description("一共多少页"),
				subsectionWithPath("numberOfElements").description("当前页的记录数"),
				subsectionWithPath("last").description("是否是最后一页"),
				subsectionWithPath("first").description("是否是第一页"),
				subsectionWithPath("empty").description("此次查询到的资源是否为空"),
				subsectionWithPath("sort").description("排序信息"));
	}
	private  RequestParametersSnippet springPageRequestFields() {
		return requestParameters(parameterWithName("page").description("第几页，从0开始"),
				parameterWithName("size").description("每页多少条记录"));
	}
}
