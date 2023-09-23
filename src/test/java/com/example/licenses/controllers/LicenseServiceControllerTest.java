package com.example.licenses.controllers;

import com.example.licenses.clients.OrganizationFeignClient;
import com.example.licenses.model.Organization;
import com.example.licenses.services.LicenseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LicenseServiceControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LicenseService licenseService;

    @MockBean
    OrganizationFeignClient organizationFeignClient;

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

//        Field fieldOrganizationFeignClient = LicenseService.class.getDeclaredField("organizationFeignClient");
//        fieldOrganizationFeignClient.setAccessible(true);
//        fieldOrganizationFeignClient.set(licenseService, this.organizationFeignClient);
    }

    @Test
    public void testFindAll() throws Exception {
        when(organizationFeignClient.getOrganization("e254f8c-c442-4ebe-a82a-e2fc1d1ff78a")).thenReturn(new Organization("nanjing"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizations/e254f8c-c442-4ebe-a82a-e2fc1d1ff78a/licenses/f3831f8c-c338-4ebe-a82a-e2fc1d1ff78a")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.organizationName", is("nanjing")))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }
}