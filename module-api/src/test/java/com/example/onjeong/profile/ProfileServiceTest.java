package com.example.onjeong.profile;

import com.example.onjeong.S3.S3Uploader;
import com.example.onjeong.family.domain.Family;
import com.example.onjeong.profile.domain.*;
import com.example.onjeong.profile.dto.*;
import com.example.onjeong.profile.repository.ExpressionRepository;
import com.example.onjeong.profile.repository.FavoriteRepository;
import com.example.onjeong.profile.repository.HateRepository;
import com.example.onjeong.profile.repository.InterestRepository;
import com.example.onjeong.profile.service.ProfileService;
import com.example.onjeong.user.domain.User;
import com.example.onjeong.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private AuthUtil authUtil;

    @Mock
    private ProfileUtil profileUtil;

    @Mock
    private S3Uploader s3Uploader;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private HateRepository hateRepository;

    @Mock
    private ExpressionRepository expressionRepository;

    @Mock
    private InterestRepository interestRepository;


    @Test
    void 가족_프로필_중_구성원_보여주기(){
        //given
        final Family family= FamilyUtils.getRandomFamily();
        final User user= UserUtils.getRandomUser(family);

        doReturn(user).when(authUtil).getUserByAuthentication();


        //when
        List<AllUserOfFamilyDto> result= profileService.getAllUserOfFamily();


        //then
        assertThat(result.size()).isEqualTo(0);
    }


    @Test
    void 프로필_상단에_개인정보_보여주기(){
        //given
        final Family family= FamilyUtils.getRandomFamily();
        final User user= UserUtils.getRandomUser(family);
        final Profile profile= ProfileUtils.getRandomProfile(family, user);

        doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
        doReturn(profile).when(profileUtil)  .getProfileByUser(user);


        //when
        UserInformationDto result= profileService.getUserInformation(user.getUserId());


        //then
        assertThat(result.getProfileImageUrl()).isEqualTo(profile.getProfileImageUrl());
        assertThat(result.isCheckProfileImage()).isEqualTo(profile.isCheckProfileImage());
    }


    @Test
    void 프로필_사진_등록하기() throws IOException {
        //given
        final Family family= FamilyUtils.getRandomFamily();
        final User user= UserUtils.getRandomUser(family);
        final Profile profile= ProfileUtils.getRandomProfile(family, user);
        final MockMultipartFile imgFile= new MockMultipartFile("images","","image/png", FileInputStream.nullInputStream());

        ReflectionTestUtils.setField(profileService, "AWS_S3_BUCKET_URL", "");

        doReturn(user).when(authUtil).getUserByAuthentication();
        doReturn(profile).when(profileUtil).getProfileByUser(user);


        //when
        Profile result= profileService.registerProfileImage(imgFile);


        //then
        assertThat(result.getProfileImageUrl()).isEqualTo(null);
        assertThat(result.isCheckProfileImage()).isEqualTo(true);
        assertThat(result.isCheckProfileUpload()).isEqualTo(true);
    }


    @Test
    void 프로필_사진_삭제하기(){
        //given
        final Family family= FamilyUtils.getRandomFamily();
        final User user= UserUtils.getRandomUser(family);
        final Profile profile= ProfileUtils.getRandomProfile(family, user);

        ReflectionTestUtils.setField(profileService, "AWS_S3_BUCKET_URL", "");

        doReturn(user).when(authUtil).getUserByAuthentication();
        doReturn(profile).when(profileUtil).getProfileByUser(user);


        //when
        profileService.deleteProfileImage();


        //then
        assertThat(profile.getProfileImageUrl()).isEqualTo(null);
        assertThat(profile.isCheckProfileImage()).isEqualTo(false);
        verify(s3Uploader,times(1)).deleteFile(any(String.class));
    }


    @Nested
    class 상태메시지{
        @Test
        void 조회(){
            //given
            final Family family= FamilyUtils.getRandomFamily();
            final User user= UserUtils.getRandomUser(family);
            final Profile profile= ProfileUtils.getRandomProfile(family, user);

            doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
            doReturn(profile).when(profileUtil).getProfileByUser(user);


            //when
            ProfileMessageDto result= profileService.getProfileMessage(user.getUserId());


            //then
            assertThat(result.getMessage()).isEqualTo(profile.getMessage());
        }

        @Test
        void 작성(){
            //given
            final Family family= FamilyUtils.getRandomFamily();
            final User user= UserUtils.getRandomUser(family);
            final Profile profile= ProfileUtils.getRandomProfile(family, user);
            final ProfileMessageDto profileMessageDto= profileMessageDto("profile message to register");

            doReturn(user).when(authUtil).getUserByAuthentication();
            doReturn(profile).when(profileUtil).getProfileByUser(user);


            //when
            profileService.registerProfileMessage(profileMessageDto);


            //then
            assertThat(profile.getMessage()).isEqualTo("profile message to register");
            assertThat(profile.isCheckProfileUpload()).isEqualTo(true);
        }

        @Test
        void 수정(){
            //given
            final Family family= FamilyUtils.getRandomFamily();
            final User user= UserUtils.getRandomUser(family);
            final Profile profile= ProfileUtils.getRandomProfile(family, user);
            final ProfileMessageDto profileMessageDto= profileMessageDto("profile message to modify");

            doReturn(user).when(authUtil).getUserByAuthentication();
            doReturn(profile).when(profileUtil).getProfileByUser(user);


            //when
            profileService.modifyProfileMessage(profileMessageDto);


            //then
            assertThat(profile.getMessage()).isEqualTo("profile message to modify");
        }
    }


    @Test
    void 유저_개인정보와상태메시지_조회(){
        //given
        final Family family= FamilyUtils.getRandomFamily();
        final User user= UserUtils.getRandomUser(family);
        final Profile profile= ProfileUtils.getRandomProfile(family, user);

        doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
        doReturn(profile).when(profileUtil).getProfileByUser(user);


        //when
        UserInformationAndProfileMessageDto result= profileService.getUserInformationAndProfileMessage(user.getUserId());


        //then
        assertThat(result.getProfileImageUrl()).isEqualTo(profile.getProfileImageUrl());
        assertThat(result.isCheckProfileImage()).isEqualTo(profile.isCheckProfileImage());
        assertThat(result.getMessage()).isEqualTo(profile.getMessage());
    }


    @Nested
    class 자기소개답변{
        @Test
        void 답변리스트_조회(){
            //given
            final Family family= FamilyUtils.getRandomFamily();
            final User user= UserUtils.getRandomUser(family);
            final Profile profile= ProfileUtils.getRandomProfile(family, user);

            addSelfIntroductionAnswer(profile);

            doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
            doReturn(profile).when(profileUtil).getProfileByUser(user);


            //when
            SelfIntroductionAnswerListGetDto result= profileService.getSelfIntroductionAnswer(user.getUserId());


            //then
            assertThat(result.getFavorites().size()).isEqualTo(1);
            assertThat(result.getHates().size()).isEqualTo(2);
            assertThat(result.getExpressions().size()).isEqualTo(3);
            assertThat(result.getInterests().size()).isEqualTo(4);
        }

        @Nested
        class 답변작성{
            @Test
            void 답변종류가_favorite인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final SelfIntroductionAnswerRegisterDto selfIntroductionAnswerRegisterDto=
                        selfIntroductionAnswerRegisterDto("answer to register");

                ProfileUtils.updateCheckProfileUploadToFalse(profile);

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.registerSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerRegisterDto, SelfIntroductionType.FAVORITE);


                //then
                assertThat(profile.isCheckProfileUpload()).isEqualTo(true);
                verify(favoriteRepository,times(1)).save(any(Favorite.class));
            }

            @Test
            void 답변종류가_hate인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final SelfIntroductionAnswerRegisterDto selfIntroductionAnswerRegisterDto=
                        selfIntroductionAnswerRegisterDto("answer to register");

                ProfileUtils.updateCheckProfileUploadToFalse(profile);

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.registerSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerRegisterDto, SelfIntroductionType.HATE);


                //then
                assertThat(profile.isCheckProfileUpload()).isEqualTo(true);
                verify(hateRepository,times(1)).save(any(Hate.class));
            }

            @Test
            void 답변종류가_expression인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final SelfIntroductionAnswerRegisterDto selfIntroductionAnswerRegisterDto=
                        selfIntroductionAnswerRegisterDto("answer to register");

                ProfileUtils.updateCheckProfileUploadToFalse(profile);

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.registerSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerRegisterDto, SelfIntroductionType.EXPRESSION);


                //then
                assertThat(profile.isCheckProfileUpload()).isEqualTo(true);
                verify(expressionRepository,times(1)).save(any(Expression.class));
            }

            @Test
            void 답변종류가_interest인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final SelfIntroductionAnswerRegisterDto selfIntroductionAnswerRegisterDto=
                        selfIntroductionAnswerRegisterDto("answer to register");

                ProfileUtils.updateCheckProfileUploadToFalse(profile);

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.registerSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerRegisterDto, SelfIntroductionType.INTEREST);


                //then
                assertThat(profile.isCheckProfileUpload()).isEqualTo(true);
                verify(interestRepository,times(1)).save(any(Interest.class));
            }
        }

        @Nested
        class 답변삭제{
            @Test
            void 답변종류가_favorite인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final Long selfIntroductionAnswerId= 1L;

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.deleteSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerId, SelfIntroductionType.FAVORITE);


                //then
                verify(favoriteRepository,times(1)).deleteByFavoriteIdAndProfile(any(Long.class), any(Profile.class));
            }

            @Test
            void 답변종류가_hate인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final Long selfIntroductionAnswerId= 1L;

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.deleteSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerId, SelfIntroductionType.HATE);


                //then
                verify(hateRepository,times(1)).deleteByHateIdAndProfile(any(Long.class), any(Profile.class));
            }

            @Test
            void 답변종류가_expression인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final Long selfIntroductionAnswerId= 1L;

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.deleteSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerId, SelfIntroductionType.EXPRESSION);


                //then
                verify(expressionRepository,times(1)).deleteByExpressionIdAndProfile(any(Long.class), any(Profile.class));
            }

            @Test
            void 답변종류가_interest인경우(){
                //given
                final Family family= FamilyUtils.getRandomFamily();
                final User user= UserUtils.getRandomUser(family);
                final Profile profile= ProfileUtils.getRandomProfile(family, user);
                final Long selfIntroductionAnswerId= 1L;

                doReturn(user).when(authUtil).getUserByUserId(user.getUserId());
                doReturn(profile).when(profileUtil).getProfileByUser(user);


                //when
                profileService.deleteSelfIntroductionAnswer(user.getUserId(), selfIntroductionAnswerId, SelfIntroductionType.INTEREST);


                //then
                verify(interestRepository,times(1)).deleteByInterestIdAndProfile(any(Long.class), any(Profile.class));
            }
        }
    }


    private ProfileMessageDto profileMessageDto(String message){
        return ProfileMessageDto.builder()
                .message(message)
                .build();
    }

    private void addSelfIntroductionAnswer(Profile profile){
        for(int i=0;i<1;i++){
            profile.getFavorites().add(FavoriteUtils.getRandomFavorite(profile));
        }
        for(int i=0;i<2;i++){
            profile.getHates().add(HateUtils.getRandomHate(profile));
        }
        for(int i=0;i<3;i++){
            profile.getExpressions().add(ExpressionUtils.getRandomExpression(profile));
        }
        for(int i=0;i<4;i++){
            profile.getInterests().add(InterestUtils.getRandomInterest(profile));
        }
    }

    private SelfIntroductionAnswerRegisterDto selfIntroductionAnswerRegisterDto(String selfIntroductionAnswerContent){
        return SelfIntroductionAnswerRegisterDto.builder()
                .selfIntroductionAnswerContent(selfIntroductionAnswerContent)
                .build();
    }
}