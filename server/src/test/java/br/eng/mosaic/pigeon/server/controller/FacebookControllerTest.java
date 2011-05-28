package br.eng.mosaic.pigeon.server.controller;

import java.net.URL;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.eng.mosaic.pigeon.server.integration.PigeonConfiguration;
import br.eng.mosaic.pigeon.server.socialnetwork.FacebookConfiguration;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.RequestMethod;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ScopePermission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:br/eng/mosaic/pigeon/cfg/spring/spring-test-beans.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class FacebookControllerTest extends TestCase {
	

	@Autowired private FacebookController facebookController;
	@Autowired private ApplicationContext applicationContext;
	@Autowired private PigeonConfiguration pigeonConfig;
	@Autowired private FacebookConfiguration fbConfig;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private HandlerAdapter handlerAdapter;

	private static String bar = "/";
	
	public void setFacebookController(FacebookController facebookController) {
		this.facebookController = facebookController;
	}

	@Before public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
		facebookController = new FacebookController();
	}

	@Test public void testSignInSuccessfully() throws Exception {
		request.setRequestURI( bar + FacebookController.uri_fb.sign_in );
		ModelAndView view = handle(request, response);
		assertNotNull( view );
		
		assertTrue( view.getViewName().startsWith( "redirect:" ) );
		assertTrue( view.getViewName().contains( pigeonConfig.fb_root ) );
		
		assertTrue( view.getViewName().contains( RequestMethod.fb_oauth_authorize.method ) );
		assertTrue( view.getViewName().contains( "client_id=" + fbConfig.id ) );
		
		String redirect_uri =  "redirect_uri=" 
			+ pigeonConfig.app_root
			+ FacebookController.uri_fb.sign_callback;
		assertTrue( view.getViewName().contains( redirect_uri ) );
		assertTrue( view.getViewName().contains( "scope=" + ScopePermission.all() ) );
		
		String realUrl = view.getViewName().replace("redirect:", "");
		URL url = new URL( realUrl );
		assertNotNull( url.openConnection() );
	}

	@Ignore @Test public void testCallbackSuccessfully() throws Exception {
		throw new NotImplementedException();
	}
	
	@Ignore @Test public void testCallbackWhenServerCrash() throws Exception {
		request.setRequestURI( bar + FacebookController.uri_fb.sign_callback );
		request.setParameter("code", "" + new Random().nextInt( Integer.MAX_VALUE ));
		ModelAndView view = handle(request, response);
	}
	
	@Test public void testTest() throws Exception {
		request.setRequestURI( "test" );
		ModelAndView view = handle(request, response);
	}

	private ModelAndView handle( HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
        final HandlerMapping handlerMapping = applicationContext.getBean(HandlerMapping.class);
        final HandlerExecutionChain handler = handlerMapping.getHandler(request);
		
		assertNotNull( "No handler found for request, check you request mapping", handler);

		final Object controller = handler.getHandler();

		final HandlerInterceptor[] interceptors = 
			applicationContext.getBean(HandlerMapping.class).getHandler(request).getInterceptors();
		
		for (HandlerInterceptor interceptor : interceptors) {
			final boolean carryOn = interceptor.preHandle(
					request, response, controller);
			if (!carryOn) {
				return null;
			}
		}

		final ModelAndView mav = handlerAdapter.handle(request, response, controller);
		return mav;
	}
	
	public static void main(String[] args) throws JSONException {
		String x = "{ 'name' : { 'value' : 'fsdfsdf' } }";
		JSONObject obj = new JSONObject(x);
		System.out.println( obj.get( "name" ) );
	}

}