package com.example.onjeong.profile.service;

import com.example.onjeong.S3.S3Uploader;
import com.example.onjeong.profile.domain.*;
import com.example.onjeong.profile.dto.*;
import com.example.onjeong.profile.repository.*;
import com.example.onjeong.user.domain.User;
import com.example.onjeong.util.AuthUtil;
import com.example.onjeong.util.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final FavoriteRepository favoriteRepository;
    private final HateRepository hateRepository;
    private final ExpressionRepository expressionRepository;
    private final InterestRepository interestRepository;
    private final S3Uploader s3Uploader;
    private final AuthUtil authUtil;
    private final ProfileUtil profileUtil;

    @Value("${cloud.aws.s3.bucket.url}")
    private String AWS_S3_BUCKET_URL;


    //가족 프로필 중 구성원 보여주기
    @Transactional(readOnly = true)
    public List<AllUserOfFamilyDto> getAllUserOfFamily(){
        final User loginUser= authUtil.getUserByAuthentication();
        final List<User> usersOfFamily= loginUser.getFamily().getUsers();
        final List<AllUserOfFamilyDto> result= new ArrayList<>();
        for(User u:usersOfFamily){
            final AllUserOfFamilyDto allUserOfFamilyDto = AllUserOfFamilyDto.builder()
                    .userId(u.getUserId())
                    .userStatus(u.getUserStatus())
                    .profileImageUrl(u.getProfile().getProfileImageUrl())
                    .build();
            result.add(allUserOfFamilyDto);
        }
        return result;
    }

    //프로필 상단에 개인 정보 보여주기
    @Transactional(readOnly = true)
    public UserInformationDto getUserInformation(final Long userId){
        final User user= authUtil.getUserByUserId(userId);
        final Profile profile= profileUtil.getProfileByUser(user);
        return UserInformationDto.builder().user(user).profileImageUrl(profile.getProfileImageUrl())
                .checkProfileImage(profile.isCheckProfileImage()).build();
    }

    //프로필 사진 등록하기
    @Transactional
    public Profile registerProfileImage(final MultipartFile multipartFile){
        final User loginUser= authUtil.getUserByAuthentication();
        final Profile profile= profileUtil.getProfileByUser(loginUser);
        if(profile.isCheckProfileImage()) s3Uploader.deleteFile(profile.getProfileImageUrl().substring(AWS_S3_BUCKET_URL.length()));
        final String imageUrl= s3Uploader.upload(multipartFile, "profile");
        profile.updateProfileImageUrl(imageUrl);
        profile.updateCheckProfileImage(true);
        profile.updateCheckProfileUpload(true);
        return profile;
    }

    //프로필 사진 삭제하기
    @Transactional
    public void deleteProfileImage(){
        final User loginUser= authUtil.getUserByAuthentication();
        final Profile profile= profileUtil.getProfileByUser(loginUser);
        final String deletedImageUrl= profile.getProfileImageUrl();
        final String fileName = deletedImageUrl.substring(AWS_S3_BUCKET_URL.length());
        s3Uploader.deleteFile(fileName);
        profile.updateProfileImageUrl(null);
        profile.updateCheckProfileImage(false);
    }

    //상태메시지 보여주기
    @Transactional(readOnly = true)
    public ProfileMessageDto getProfileMessage(final Long userId){
        final User user= authUtil.getUserByUserId(userId);
        final Profile profile= profileUtil.getProfileByUser(user);
        return ProfileMessageDto.builder()
                .message(profile.getMessage())
                .build();
    }

    //상태메시지 작성하기
    @Transactional
    public Profile registerProfileMessage(final ProfileMessageDto profileMessageDto){
        final User loginUser= authUtil.getUserByAuthentication();
        final Profile profile= profileUtil.getProfileByUser(loginUser);
        profile.updateMessage(profileMessageDto.getMessage());
        profile.updateCheckProfileUpload(true);
        return profile;
    }

    //상태메시지 수정하기
    @Transactional
    public void modifyProfileMessage(final ProfileMessageDto profileMessageDto){
        final User loginUser= authUtil.getUserByAuthentication();
        final Profile profile= profileUtil.getProfileByUser(loginUser);
        profile.updateMessage(profileMessageDto.getMessage());
    }


    //유저 개인정보+상태메시지 보여주기
    @Transactional(readOnly = true)
    public UserInformationAndProfileMessageDto getUserInformationAndProfileMessage(final Long userId){
        final User user= authUtil.getUserByUserId(userId);
        final Profile profile= profileUtil.getProfileByUser(user);
        return UserInformationAndProfileMessageDto.builder().user(user).profileImageUrl(profile.getProfileImageUrl())
                .checkProfileImage(profile.isCheckProfileImage()).message(profile.getMessage()).build();
    }

    //자기소개 답변 목록 보여주기
    @Transactional(readOnly = true)
    public SelfIntroductionAnswerListGetDto getSelfIntroductionAnswer(final Long userId){
        final User user= authUtil.getUserByUserId(userId);
        final Profile profile= profileUtil.getProfileByUser(user);
        final List<SelfIntroductionAnswerGetDto> favorites= getFavorites(profile);
        final List<SelfIntroductionAnswerGetDto> hates= getHates(profile);
        final List<SelfIntroductionAnswerGetDto> expressions= getExpressions(profile);
        final List<SelfIntroductionAnswerGetDto> interests= getInterests(profile);
        return SelfIntroductionAnswerListGetDto.builder().favorites(favorites).hates(hates)
                .expressions(expressions).interests(interests).build();
    }

    //자기소개 답변 작성하기
    @Transactional
    public Profile registerSelfIntroductionAnswer(final Long userId, final SelfIntroductionAnswerRegisterDto selfIntroductionAnswerRegisterDto, final SelfIntroductionType category){
        final User user= authUtil.getUserByUserId(userId);
        final Profile profile= profileUtil.getProfileByUser(user);
        switch(category){
            case FAVORITE:
                final Favorite favorite= Favorite.builder()
                        .favoriteContent(selfIntroductionAnswerRegisterDto.getSelfIntroductionAnswerContent())
                        .profile(profile)
                        .build();
                favoriteRepository.save(favorite);
                break;
            case HATE:
                final Hate hate= Hate.builder()
                        .hateContent(selfIntroductionAnswerRegisterDto.getSelfIntroductionAnswerContent())
                        .profile(profile)
                        .build();
                hateRepository.save(hate);
                break;
            case EXPRESSION:
                final Expression expression= Expression.builder()
                        .expressionContent(selfIntroductionAnswerRegisterDto.getSelfIntroductionAnswerContent())
                        .profile(profile)
                        .build();
                expressionRepository.save(expression);
                break;
            case INTEREST:
                final Interest interest= Interest.builder()
                        .interestContent(selfIntroductionAnswerRegisterDto.getSelfIntroductionAnswerContent())
                        .profile(profile)
                        .build();
                interestRepository.save(interest);
                break;
        }
        profile.updateCheckProfileUpload(true);
        return profile;
    }

    //자기소개 답변 삭제하기
    @Transactional
    public void deleteSelfIntroductionAnswer(final Long userId, final Long selfIntroductionAnswerId, final SelfIntroductionType category){
        final User user= authUtil.getUserByUserId(userId);
        final Profile profile= profileUtil.getProfileByUser(user);
        switch(category){
            case FAVORITE:
                favoriteRepository.deleteByFavoriteIdAndProfile(selfIntroductionAnswerId, profile);
                break;
            case HATE:
                hateRepository.deleteByHateIdAndProfile(selfIntroductionAnswerId, profile);
                break;
            case EXPRESSION:
                expressionRepository.deleteByExpressionIdAndProfile(selfIntroductionAnswerId, profile);
                break;
            case INTEREST:
                interestRepository.deleteByInterestIdAndProfile(selfIntroductionAnswerId, profile);
                break;
        }
    }


    public boolean checkProfileUpload(){
        final User loginUser= authUtil.getUserByAuthentication();
        final Profile profile= profileUtil.getProfileByUser(loginUser);
        return profile.isCheckProfileUpload();
    }

    private List<SelfIntroductionAnswerGetDto> getFavorites(final Profile profile){
        final List<SelfIntroductionAnswerGetDto> result= new ArrayList<>();
        final List<Favorite> favorites= profile.getFavorites();
        for(Favorite f:favorites){
            final SelfIntroductionAnswerGetDto selfIntroductionAnswerGetDto= SelfIntroductionAnswerGetDto.builder()
                    .selfIntroductionAnswerId(f.getFavoriteId())
                    .selfIntroductionAnswerContent(f.getFavoriteContent())
                    .build();
            result.add(selfIntroductionAnswerGetDto);
        }
        return result;
    }

    private List<SelfIntroductionAnswerGetDto> getHates(final Profile profile){
        final List<SelfIntroductionAnswerGetDto> result= new ArrayList<>();
        final List<Hate> hates= profile.getHates();
        for(Hate f:hates){
            final SelfIntroductionAnswerGetDto selfIntroductionAnswerGetDto= SelfIntroductionAnswerGetDto.builder()
                    .selfIntroductionAnswerId(f.getHateId())
                    .selfIntroductionAnswerContent(f.getHateContent())
                    .build();
            result.add(selfIntroductionAnswerGetDto);
        }
        return result;
    }

    private List<SelfIntroductionAnswerGetDto> getExpressions(final Profile profile){
        final List<SelfIntroductionAnswerGetDto> result= new ArrayList<>();
        final List<Expression> expressions= profile.getExpressions();
        for(Expression e:expressions){
            final SelfIntroductionAnswerGetDto selfIntroductionAnswerGetDto= SelfIntroductionAnswerGetDto.builder()
                    .selfIntroductionAnswerId(e.getExpressionId())
                    .selfIntroductionAnswerContent(e.getExpressionContent())
                    .build();
            result.add(selfIntroductionAnswerGetDto);
        }
        return result;
    }

    private List<SelfIntroductionAnswerGetDto> getInterests(final Profile profile){
        final List<SelfIntroductionAnswerGetDto> result= new ArrayList<>();
        final List<Interest> interests= profile.getInterests();
        for(Interest i:interests){
            final SelfIntroductionAnswerGetDto selfIntroductionAnswerGetDto= SelfIntroductionAnswerGetDto.builder()
                    .selfIntroductionAnswerId(i.getInterestId())
                    .selfIntroductionAnswerContent(i.getInterestContent())
                    .build();
            result.add(selfIntroductionAnswerGetDto);
        }
        return result;
    }
}
